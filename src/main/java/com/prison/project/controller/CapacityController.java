package com.prison.project.controller;

import com.prison.project.exception.NotFoundException;
import com.prison.project.model.PrisonCapacity;
import com.prison.project.service.prisonCapacity.PrisonCapacityCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/prison-management-system/capacities")
public class CapacityController {

    private final PrisonCapacityCheck prisonCapacityCheck;
    private final PrisonCapacity prisonCapacity;

    @GetMapping("/capacity-now")
    public String capacityNow(Model map) {

        Long freeSpaces = prisonCapacityCheck.getFreePlacesByDate(LocalDate.now());
        map.addAttribute("prisonFreePlaces", "Prison currently has: " + freeSpaces + " free places");
        map.addAttribute("errorFromController","Registering of new prisoner denied because there are now free places in prison now");

        if (freeSpaces < 1) {

            try {
                LocalDate closestDayWithFreeSpaces = prisonCapacityCheck.getClosestDateWithFreePlaces(LocalDate.now());
                map.addAttribute("closestDay", "Closest date with free places: " + closestDayWithFreeSpaces + "");

                Long freePlacesOnTheClosestDay = prisonCapacityCheck.getNumberOfFreePlacesInClosestDate(closestDayWithFreeSpaces);
                map.addAttribute("freePlacesOnClosestDate", "Number of free places on closest date: " + freePlacesOnTheClosestDay + "");
            } catch (NotFoundException n) {
                map.addAttribute("noPrisonerExists", "There are no prisoners registered in the system");
                return "capacity-index";
            }
        }

        return "capacity-index";
    }
}
