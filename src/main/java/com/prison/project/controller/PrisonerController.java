package com.prison.project.controller;

import com.prison.project.model.Crime;
import com.prison.project.model.Prisoner;
import com.prison.project.model.Punishment;
import com.prison.project.service.crime.GetCrimeService;
import com.prison.project.service.prisoner.CreatePrisonerService;
import com.prison.project.service.prisoner.DeletePrisonerService;
import com.prison.project.service.prisoner.GetPrisonerService;
import com.prison.project.service.prisoner.UpdatePrisonerService;
import com.prison.project.service.punishment.GetPunishmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
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
    public String register(@Valid Prisoner prisoner, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "prisoner-add";
        }

        createPrisonerService.registerPrisoner(prisoner);


        return prisonerIndex(model);//"redirect:/prison-management-system/prisoners";
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

       updatePrisonerService.updatePrisoner(id,prisoner);

        return prisonerIndex(model);//"redirect:/prison-management-system/prisoners";
    }

}
