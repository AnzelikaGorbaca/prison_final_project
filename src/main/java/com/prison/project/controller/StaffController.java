package com.prison.project.controller;

import com.prison.project.model.Occupation;
import com.prison.project.model.PrisonCapacity;
import com.prison.project.model.Staff;
import com.prison.project.service.prisonCapacity.PrisonCapacityCheck;
import com.prison.project.service.staff.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/prison-management-system/staffs")
public class StaffController {

    private final CreateStaffService createStaffService;
    private final DeleteStaffService deleteStaffService;
    private final GetStaffService getStaffService;
    private final UpdateStaffService updateStaffService;
    private final OccupationEnumSorting occupationEnumSorting;
  //  private final PrisonCapacityCheck prisonCapacityCheck;


    @GetMapping
    public String staffStart(Model model) {
        model.addAttribute("pageName", "All Staff");
        model.addAttribute("staffs", getStaffService.findAllStaff());
        return "staff-index";
    }

    @GetMapping("/staff-add")
    public String staffAdd(Model map, Staff staff) {
        map.addAttribute("pageName", "Add New Staff Member");

        List<Occupation> occupationList = occupationEnumSorting.getSortedList();
        map.addAttribute("occupationList",occupationList);

      //  map.addAttribute("prisonFreePlaces","Prison currently has " +prisonCapacityCheck.getFreePlacesNow()+" free places");


        return "staff-add";
    }

    @GetMapping("/delete/{id}")
    public String deleteStaffById(@PathVariable("id") Long id, Model model) {
        deleteStaffService.deleteStaff(id);
        return "redirect:/prison-management-system/staffs";
    }

    @GetMapping("/edit/{id}")
    public String editStaffById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageName", "Edit Staff Member Profile");

        Staff staff = getStaffService.findStaffById(id);
        model.addAttribute("staff", staff);

        List<Occupation> occupationList = occupationEnumSorting.getSortedList();
        model.addAttribute("occupationList",occupationList);

        return "staff-edit";

    }

    @PostMapping
    public String registerStaff(@Valid Staff staff, BindingResult result, Model model) {

        if (result.hasErrors()) {
            staffAdd(model,staff);
            return "staff-add";
        }
            List<Staff> staffList = getStaffService.findAllStaff();
            for (Staff s : staffList) {
                if (staff.getPersonalCode().contains(s.getPersonalCode())) {
                    model.addAttribute("errorFromController", "Staff member with personal code " + s.getPersonalCode() + " already exists");
                    staffAdd(model,staff);
                    return "staff-add";
                }
            }
        createStaffService.registerStaff(staff);
        return staffStart(model);
    }

    @PostMapping("/update/{id}")
    public String updateStaff(@PathVariable("id") Long id, @Valid Staff staff, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Occupation> occupationList = occupationEnumSorting.getSortedList();
            model.addAttribute("occupationList",occupationList);
            return "staff-edit";
        }
        try {
            updateStaffService.updateStaff(id, staff);
        } catch (RuntimeException e) {
            if (e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                if ((e.getCause().getCause()).getLocalizedMessage().contains("Duplicate entry")) {

                    String errorMessage = ((e.getCause().getCause()).getLocalizedMessage().substring(15, 30));
                    model.addAttribute("errorFromController", "Staff member with personal code " + errorMessage + " already exists");
                    List<Occupation> occupationList = occupationEnumSorting.getSortedList();
                    model.addAttribute("occupationList",occupationList);
                    return "staff-edit";
                }
            }
        }
        return staffStart(model);
    }
}
