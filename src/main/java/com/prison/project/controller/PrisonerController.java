package com.prison.project.controller;

import com.prison.project.model.*;
import com.prison.project.service.crime.GetCrimeService;
import com.prison.project.service.prisoner.*;
import com.prison.project.service.punishment.GetPunishmentService;
import com.prison.project.utilities.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

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


    @GetMapping
    public String prisonerIndex(Model model) {
        model.addAttribute("pageName", "All Prisoners");
        model.addAttribute("prisoners", getPrisonerService.getAll());
        return "prisoner-index";
    }

    @GetMapping ("/prisoner-add")
    public String signUp(Model map, Prisoner prisoner, Model model) {
        map.addAttribute("pageName", "Add New Prisoner");

        List <Punishment> punishmentList = getPunishmentService.getAllPunishments();
        List<Crime> crimeList = getCrimeService.getAllCrime();

        model.addAttribute("crimeList", crimeList);
        model.addAttribute("punishmentList", punishmentList);

        return "prisoner-add";
    }

    @PostMapping
    public String registerPrisoner(@Valid Prisoner prisoner, @RequestParam("image") MultipartFile multipartFile,BindingResult result, Model model) throws IOException {

        if (result.hasErrors()) {
            return "prisoner-add";
        }

        List<Prisoner> prisonerList = getPrisonerService.getAll();
        for (Prisoner p : prisonerList) {
            if (prisoner.getPersonalCode().contains(p.getPersonalCode())) {
                model.addAttribute("errorFromController", "Prisoner with personal code " + p.getPersonalCode() + " already exists");
                return "prisoner-add";
            }
        }

        Punishment punishment = getPunishmentService.getPunishmentById(prisoner.getPunishmentId());
        prisoner.setPunishment(punishment);

        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        prisoner.setPhoto(fileName);
        Prisoner savedPrisoner = createPrisonerService.registerPrisoner(prisoner);
        String uploadDir = "prisoner-photos/" + savedPrisoner.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);

        return prisonerIndex(model);//"redirect:/prison-management-system/prisoners";
    }



    @GetMapping(value = "/prisoner-search")
    public String searchStaff(PrisonerSearch prisonerSearch, Model model) {
        model.addAttribute("pageName", "Prisoner Search");

        List<Crime> crimeList = getCrimeService.getAllCrime();
        List<Punishment> punishmentList = getPunishmentService.getAllPunishments();

        model.addAttribute("crimeList",crimeList);
        model.addAttribute("punishmentList", punishmentList);
        return "prisoner-search";
    }

    @PostMapping (value = "/prisoner-search-result")
    public String getSearchedStaff (@Valid PrisonerSearch prisonerSearch, BindingResult bindingResult, Model model){
        model.addAttribute("pageName", "Results matching your search criteria:");

        if (bindingResult.hasErrors()){
            return "prisoner-search";
        }
        List<Prisoner> prisonerList = searchPrisonerService.searchPrisoner(prisonerSearch);
        if(prisonerList.isEmpty()){
            model.addAttribute("nothingFound", "There are no results matching your search criteria");
        }

        model.addAttribute("prisonerList", prisonerList);
        return "prisoner-search-result";
    }


    @GetMapping("/delete/{id}")
    public String deletePrisonerById(@PathVariable("id") Long id, Model model) {
        deletePrisonerService.deletePrisoner(id);
        return "redirect:/prison-management-system/prisoners";
    }

    @GetMapping("/edit/{id}")
    public String editPrisonerById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageName", "Edit Prisoner Profile");

        Prisoner prisoner = getPrisonerService.getPrisonerById(id);
        List <Punishment> punishmentList = getPunishmentService.getAllPunishments();
        List<Crime> crimeList = getCrimeService.getAllCrime();

        model.addAttribute("crimeList", crimeList);
        model.addAttribute("punishmentList", punishmentList);
        model.addAttribute("prisoner", prisoner);

        return "prisoner-edit";
    }

    @PostMapping("/update/{id}")
    public String updatePrisoner(@PathVariable("id") Long id, @Valid Prisoner prisoner, BindingResult result, Model model) {
        if (result.hasErrors()) {
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

        return prisonerIndex(model);//"redirect:/prison-management-system/prisoners";
    }

}