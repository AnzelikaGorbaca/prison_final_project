package com.prison.project.controller;

import com.prison.project.model.Prisoner;
import com.prison.project.service.prisoner.GetPrisonerService;
import com.prison.project.service.statistics.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/prison-management-system")
public class StatisticsController {

    private final GetPrisonerService getPrisonerService;
    private final StatisticsService statisticsService;

    @GetMapping("/statistics-duration")
    public String getStatisticsDuration(Model model) {
        model.addAttribute("pageName", "TOP 10 Prisoners By Imprisonment Duration");
        model.addAttribute("prisoners", getPrisonerService.getTopPrisonersByImprisonmentMonths());
        return "statistics-duration";
    }

    @GetMapping("/statistics-crimes")
    public String getStatisticsCrimes(Model model) {
        model.addAttribute("pageName", "TOP 10 Prisoners By Crimes Committed");
        model.addAttribute("prisoners", statisticsService.getListPrisonerWithCrimeCountForStatistics());
        return "statistics-crimes";
    }

}
