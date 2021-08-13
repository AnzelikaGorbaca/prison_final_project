package com.prison.project.controller;

import com.prison.project.model.Prisoner;
import com.prison.project.service.prisoner.GetPrisonerService;
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

    @GetMapping("/statistics-duration")
    public String getStatisticsDuration(Model model) {
        model.addAttribute("pageName", "TOP Prisoners By Imprisonment Duration");
        model.addAttribute("prisoners", getPrisonerService.getTopPrisonersByImprisonmentMonths());
        return "statistics-duration";
    }

    @GetMapping("/statistics-crimes")
    public String getStatisticsCrimes(Model model) {
        model.addAttribute("pageName", "TOP Prisoners By Crimes Committed");

        List<Prisoner> prisonerList = getPrisonerService.getAllWithStatus();
        Map<Prisoner, Integer> map = new HashMap<>();
        for (Prisoner p: prisonerList){
            map.put(p, p.getCrimes().size());
        }

        LinkedHashMap<Prisoner, Integer> a = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        List <Prisoner> resultList = new ArrayList(a.keySet());

        model.addAttribute("prisoners", resultList);
        return "statistics-crimes";
    }

}
