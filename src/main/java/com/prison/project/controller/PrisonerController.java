package com.prison.project.controller;

import com.prison.project.model.Crime;
import com.prison.project.model.Prisoner;
import com.prison.project.model.PrisonerSearch;
import com.prison.project.model.Punishment;
import com.prison.project.service.crime.GetCrimeService;
import com.prison.project.service.prisonCapacity.PrisonCapacityCheck;
import com.prison.project.service.prisoner.*;
import com.prison.project.service.punishment.GetPunishmentService;
import com.prison.project.utilities.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/prison-management-system/prisoners", produces = APPLICATION_JSON_VALUE)
public class PrisonerController {

    private final CreatePrisonerService createPrisonerService;
    private final DeletePrisonerService deletePrisonerService;
    private final GetPrisonerService getPrisonerService;
    private final UpdatePrisonerService updatePrisonerService;
    private final GetCrimeService getCrimeService;
    private final GetPunishmentService getPunishmentService;
    private final SearchPrisonerService searchPrisonerService;
    private final PrisonCapacityCheck prisonCapacityCheck;
    private final PhotoServicePrisoner photoServicePrisoner;


    @GetMapping
    public String prisonerIndex(Model model) {
        model.addAttribute("pageName", "All Prisoners");
        model.addAttribute("prisoners", getPrisonerService.getAllWithStatus());
        return "prisoner-index";
    }

    @GetMapping("/prisoner-add")
    public String signUp(Model map, Prisoner prisoner) {
        if (prisonCapacityCheck.getFreePlacesByDate(LocalDate.now()) < 1) {
            return "redirect:/prison-management-system/capacities/capacity-now";

        }
        map.addAttribute("pageName", "Add New Prisoner");

        List<Punishment> punishmentList = getPunishmentService.getAllPunishments();
        List<Crime> crimeList = getCrimeService.getAllCrime();

        map.addAttribute("crimeList", crimeList);
        map.addAttribute("punishmentList", punishmentList);

        return "prisoner-add";
    }

    @PostMapping
    public String registerPrisoner(@Valid Prisoner prisoner,
                                   BindingResult result, Model model,
                                   @RequestParam("image") MultipartFile multipartFile) throws IOException {

        if (result.hasErrors()) {
            signUp(model, prisoner);
            return "prisoner-add";
        }

        List<Prisoner> prisonerList = getPrisonerService.getAll();
        for (Prisoner p : prisonerList) {
            if (prisoner.getPersonalCode().contains(p.getPersonalCode())) {
                model.addAttribute("errorFromController", "Prisoner with personal code " + p.getPersonalCode() + " already exists");
                signUp(model, prisoner);
                return "prisoner-add";
            }
        }


        if (prisoner.getCrimesJson() == null) {
            model.addAttribute("errorForCrime", "Crime is required");
            signUp(model, prisoner);
            return "prisoner-add";
        }

        List<Crime> selectedCrimes = getCrimeService.getCrimesJson(prisoner.getCrimesJson());
        prisoner.setCrimes(selectedCrimes);

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        prisoner.setPhoto(fileName);
        Prisoner savedPrisoner = createPrisonerService.registerPrisoner(prisoner);
        String uploadDir = "photos/" + "prisoner_" + savedPrisoner.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);


        return prisonerIndex(model);//"redirect:/prison-management-system/prisoners";
    }


    @GetMapping(value = "/prisoner-search")
    public String searchPrisoner(PrisonerSearch prisonerSearch, Model model) {
        model.addAttribute("pageName", "Prisoner Search");

        List<Crime> crimeList = getCrimeService.getAllCrime();
        List<Punishment> punishmentList = getPunishmentService.getAllPunishmentsOrderedAsc();

        model.addAttribute("crimeList", crimeList);
        model.addAttribute("punishmentList", punishmentList);
        return "prisoner-search";
    }

    @PostMapping(value = "/prisoner-search-result")
    public String getSearchedPrisoner(@Valid PrisonerSearch prisonerSearch, BindingResult bindingResult, Model model) {
        model.addAttribute("pageName", "Results matching your search criteria:");

        if (bindingResult.hasErrors()) {
            return "prisoner-search";
        }
        List<Prisoner> prisonerList = searchPrisonerService.searchPrisoner(prisonerSearch);
        if (prisonerList.isEmpty()) {
            model.addAttribute("nothingFound", "There are no results matching your search criteria");
        }

        model.addAttribute("prisonerList", prisonerList);
        return "prisoner-search-result";
    }


    @GetMapping("/delete/{id}")
    public String deletePrisonerById(@PathVariable("id") Long id, Model model) {

        Path path = Paths.get("photos/" + "prisoner_" + id + "/" + getPrisonerService.getPrisonerById(id).getPhoto());
        FileUploadUtil.deleteFile(path);
        Path dir = Paths.get("photos/" + "prisoner_" + id);
        FileUploadUtil.deleteFile(dir);

        deletePrisonerService.deletePrisoner(id);
        return "redirect:/prison-management-system/prisoners";
    }

    @GetMapping("/profile/{id}")
    public String prisonerProfileById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageName", "Prisoner Profile");

        Prisoner prisoner = getPrisonerService.getPrisonerById(id);
        List<Punishment> punishmentList = getPunishmentService.getAllPunishments();
        List<Crime> crimeList = getCrimeService.getAllCrime();

        model.addAttribute("crimeList", crimeList);
        model.addAttribute("punishmentList", punishmentList);
        model.addAttribute("prisoner", prisoner);
        return "prisoner-profile";
    }


    @GetMapping("/update/{id}")
    public String updatePrisonerById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageName", "Edit prisoner profile");
        Prisoner prisoner = getPrisonerService.getPrisonerById(id);
        List<Punishment> punishmentList = getPunishmentService.getAllPunishments();
        List<Crime> crimeList = getCrimeService.getAllCrime();

        model.addAttribute("crimeList", crimeList);
        model.addAttribute("punishmentList", punishmentList);
        model.addAttribute("prisoner", prisoner);
        return "prisoner-edit";
    }

    @PostMapping("/update/{id}")
    public String updatePrisoner(@PathVariable("id") Long id,
                                 @Valid Prisoner prisoner,
                                 @RequestParam("image") MultipartFile multipartFile,
                                 BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "prisoner-edit";
        }


        if (prisoner.getCrimesJson() == null) {
            model.addAttribute("errorForCrime", "Crime is required");
            updatePrisonerById(id, model);
            return "prisoner-edit";
        }

        try {
            updatePrisonerService.updatePrisoner(id, prisoner);
        } catch (RuntimeException e) {
            if (e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                if ((e.getCause().getCause()).getLocalizedMessage().contains("Duplicate entry")) {
                    String errorMessage = ((e.getCause().getCause()).getLocalizedMessage().substring(15, 30));
                    model.addAttribute("errorFromController", "Prisoner with personal code " + errorMessage + " already exists");
                    return "prisoner-edit";
                }
            }
        }

        List<Crime> selectedCrimes = getCrimeService.getCrimesJson(prisoner.getCrimesJson());
        prisoner.setCrimes(selectedCrimes);

        if (!multipartFile.isEmpty()) {
            if (photoServicePrisoner.checkPhotoForErrorsAndUpload(id, prisoner, multipartFile)) {
                model.addAttribute("PhotoError", "Maximum permitted size of photo is 1048576 bytes");
                updatePrisonerById(id, model);
                return "prisoner-edit";
            }
        }




//        Prisoner savedPrisoner = updatePrisonerService.updatePrisoner(id, prisoner);


//        if (!multipartFile.isEmpty())
//            FileUploadUtil.deleteFile(Paths.get("photos/" + "prisoner_" + id + "/" + savedPrisoner.getPhoto()));
//        try {
//            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
//            String uploadDir = "photos/" + "prisoner_" + id;
//
//            if (!fileName.isEmpty()) savedPrisoner.setPhoto(fileName);
//            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
//            updatePrisonerService.updatePrisoner(id, prisoner);
//        } catch (RuntimeException | IOException e) {
//            if (e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
//                if ((e.getCause().getCause()).getLocalizedMessage().contains("Duplicate entry")) {
//                    String errorMessage = ((e.getCause().getCause()).getLocalizedMessage().substring(15, 30));
//                    model.addAttribute("errorFromController", "Prisoner with personal code " + errorMessage + " already exists");
//                    return "prisoner-edit";
//                }
//            }
//        }

        return prisonerProfileById(id, model);
    }

}