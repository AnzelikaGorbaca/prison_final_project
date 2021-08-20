package com.prison.project.controller;

import com.prison.project.exception.NotFoundException;
import com.prison.project.model.CapacityDate;
import com.prison.project.model.Occupation;
import com.prison.project.model.PrisonCapacity;
import com.prison.project.service.prisonCapacity.PrisonCapacityCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/prison-management-system/capacities")
public class CapacityController {

    private final PrisonCapacityCheck prisonCapacityCheck;


    @GetMapping("/capacity-now")
    public String capacityNow(Model map) {

        Long freeSpaces = prisonCapacityCheck.getFreePlacesByDate(LocalDate.now());
        map.addAttribute("prisonFreePlaces", "Prison currently has: " + freeSpaces + " free places");

        if (freeSpaces < 1) {
            map.addAttribute("errorFromController","Registering of new prisoners not possible because there are no free places in prison now");

            try {
                LocalDate closestDayWithFreeSpaces = prisonCapacityCheck.getClosestDateWithFreePlaces();
                map.addAttribute("closestDay", "Closest date with free places: " + closestDayWithFreeSpaces + "");
                Long freePlacesOnTheClosestDay = prisonCapacityCheck.getNumberOfFreePlacesInClosestDate();

              //  Long freePlacesOnTheClosestDay = prisonCapacityCheck.getNumberOfFreePlacesInClosestDate(closestDayWithFreeSpaces);
                map.addAttribute("freePlacesOnClosestDate", "Number of free places on closest date: " + freePlacesOnTheClosestDay + "");
            } catch (NotFoundException n) {
                map.addAttribute("noPrisonerExists", "There are no prisoners registered in the system");
                return "capacity-index";
            }
        }

        return "capacity-index";
    }

    @GetMapping("/capacity-otherDay")
    public String capacityDistinctDate(Model map, CapacityDate capacityDate) {
        map.addAttribute("pageName", "Availability searcher for date of your selection");
        CapacityDate capacityDateOld = new CapacityDate();
        capacityDateOld.setDistinctDate(capacityDate.getDistinctDate());

        return "capacity-byDate";
    }

    @PostMapping("/capacity-otherDay")
    public String capacityDistinctDateShow(Model map, CapacityDate capacityDate) {
        Long freeSpaces = prisonCapacityCheck.getFreePlacesByDate(capacityDate.getDistinctDate().minusDays(1));
        map.addAttribute("pageName1","There will be "+ freeSpaces+ " free places on the "+capacityDate.getDistinctDate()+ " by currently available data");

        return "capacity-byDate-result";
    }



}
