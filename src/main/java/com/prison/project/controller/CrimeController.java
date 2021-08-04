package com.prison.project.controller;

import com.prison.project.service.crime.CreateCrimeService;
import com.prison.project.service.crime.DeleteCrimeService;
import com.prison.project.service.crime.GetCrimeService;
import com.prison.project.service.crime.UpdateCrimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/prison-management-system/crimes")

public class CrimeController {

    private final CreateCrimeService createCrimeService;
    private final DeleteCrimeService deleteCrimeService;
    private final GetCrimeService getCrimeService;
    private final UpdateCrimeService updateCrimeService;

    @GetMapping
    public String crimeIndex(Model model){
        model.addAttribute("pageName", "All Crimes");
        model.addAttribute("crimes", getCrimeService.getAllCrime());
        return "crime-index";
    }

}
