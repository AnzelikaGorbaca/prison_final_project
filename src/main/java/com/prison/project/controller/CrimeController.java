package com.prison.project.controller;

import com.prison.project.model.Crime;
import com.prison.project.service.crime.CreateCrimeService;
import com.prison.project.service.crime.DeleteCrimeService;
import com.prison.project.service.crime.GetCrimeService;
import com.prison.project.service.crime.UpdateCrimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/prison-management-system/crimes")

public class CrimeController {

    private final CreateCrimeService createCrimeService;
    private final DeleteCrimeService deleteCrimeService;
    private final GetCrimeService getCrimeService;
    private final UpdateCrimeService updateCrimeService;

    @GetMapping
    public String crimeIndex(Model model) {
        model.addAttribute("pageName", "All Crimes");
        model.addAttribute("crimes", getCrimeService.getAllCrime());
        return "crime-index";
    }

    @GetMapping("/crime-add")
    public String addCrime(Model map, Crime crime) {
        return "crime-add";
    }

    @PostMapping
    public String registerCrime(@Valid Crime crime, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "crime-add";
        }
        List<Crime> crimeList = getCrimeService.getAllCrime();
        for (Crime c : crimeList) {
            if (crime.getCrimeDescription().equals(c.getCrimeDescription())) {
                model.addAttribute("errorFromController", "Crime " + c.getCrimeDescription() + " already exists");
                return "crime-add";
            }
        }
        createCrimeService.registerCrime(crime);
        return crimeIndex(model);
    }

    @GetMapping("/update/{id}")
    public String updateCrimeById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageName", "Edit Crime");
        Crime crime = getCrimeService.getCrimeById(id);
        model.addAttribute("crime", crime);

        return "crime-edit";
    }

    @PostMapping("/update/{id}")
    public String updateCrime(@PathVariable("id") Long id, @Valid Crime crime, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "crime-edit";
        }
        List<Crime> crimeList = getCrimeService.getAllCrime();
        for (Crime c : crimeList) {
            if (crime.getCrimeDescription().equals(c.getCrimeDescription())) {
                model.addAttribute("errorFromController", "Crime " + c.getCrimeDescription() + " already exists");
                return "crime-edit";
            }
        }
        updateCrimeService.updateCrime(id, crime);
        return crimeIndex(model);
    }

    @GetMapping("/delete/{id}")
    public String deleteCrime(@PathVariable("id") Long id, Model model) {
        List<Long> crimeList = getCrimeService.getCrimePrisoner(id);
        if (!crimeList.isEmpty()) {
            model.addAttribute("errorFromController", "Can't delete crime with id: " + id + ". It is used for prisoner data!");
        } else {
            deleteCrimeService.deleteCrimeById(id);
        }
        return crimeIndex(model);
    }
}
