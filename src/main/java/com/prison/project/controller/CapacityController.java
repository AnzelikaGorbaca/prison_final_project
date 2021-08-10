package com.prison.project.controller;

import com.prison.project.model.Occupation;
import com.prison.project.model.Staff;
import com.prison.project.service.prisonCapacity.PrisonCapacityCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/prison-management-system/capacities")
public class CapacityController {

    private final PrisonCapacityCheck prisonCapacityCheck;

 /*   @GetMapping("/capacity-now")
    public String capacityShow(Model map) {
        LocalDate today = LocalDate.now();
        Long freeSpaces = prisonCapacityCheck.getFreePlacesNow(today);
        map.addAttribute("prisonFreePlaces","Prison currently has " +freeSpaces+" free places");


        return "capacity-index";
    }*/

}
