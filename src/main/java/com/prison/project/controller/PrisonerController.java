package com.prison.project.controller;

import com.prison.project.model.Prisoner;
import com.prison.project.service.prisoner.CreatePrisonerService;
import com.prison.project.service.prisoner.DeletePrisonerService;
import com.prison.project.service.prisoner.GetPrisonerService;
import com.prison.project.service.prisoner.UpdatePrisonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/prisoner", produces = APPLICATION_JSON_VALUE)
public class PrisonerController {

    private final CreatePrisonerService createPrisonerService;
    private final DeletePrisonerService deletePrisonerService;
    private final GetPrisonerService getPrisonerService;
    private final UpdatePrisonerService updatePrisonerService;


    @GetMapping
    public String prisonerIndex(Model model) {
        model.addAttribute("pageName", "All Prisoners");
        model.addAttribute("prisoners", getPrisonerService.getAll());
        return "prisoner-index";
    }

    @GetMapping ("/prisoner-add")
    public String signUp(Model map, Prisoner prisoner) {
        map.addAttribute("pageName", "Add New Prisoner");

        return "prisoner-add";
    }

    @PostMapping //("/prisoner-add")
    public String register(@Valid Prisoner prisoner, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "prisoner-add";
        }

        createPrisonerService.registerPrisoner(prisoner);

        return "redirect:/prisoner";//redirect to main
    }

    @GetMapping("/delete/{id}")
    public String deletePrisonerById(@PathVariable("id") Long id, Model model) {
        deletePrisonerService.deletePrisoner(id);
        return "redirect:/prisoner";
    }

    @GetMapping("/edit/{id}")
    public String editPrisonerById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageName", "Edit Prisoner Profile");

        Prisoner prisoner = getPrisonerService.getPrisonerById(id);
        model.addAttribute("prisoner", prisoner);

        return "prisoner-edit";
    }

    @PostMapping("/update/{id}")
    public String updatePrisoner(@PathVariable("id") Long id, @Valid Prisoner prisoner, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "prisoner-edit";
        }

       updatePrisonerService.updatePrisoner(id,prisoner);

        return "redirect:/prisoner";
    }

}
