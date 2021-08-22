package com.prison.project.controller;

import com.prison.project.model.Staff;
import com.prison.project.model.StaffSearch;
import com.prison.project.service.photo.PhotoServiceStaff;
import com.prison.project.service.staff.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;


@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/prison-management-system/staffs")
public class StaffController {

    private final CreateStaffService createStaffService;
    private final DeleteStaffService deleteStaffService;
    private final GetStaffService getStaffService;
    private final UpdateStaffService updateStaffService;
    private final OccupationEnumSorting occupationEnumSorting;
    private final SearchStaffService searchStaffService;
    private final PhotoServiceStaff photoServiceStaff;


    @GetMapping
    public String staffStart(Model model) {
        model.addAttribute("pageName", "All Staff Members");
        model.addAttribute("staffs", getStaffService.findAllStaff());
        return "staff-index";
    }

    @GetMapping("/staff-add")
    public String staffAdd(Model map, Staff staff) {
        map.addAttribute("pageName", "Add New Staff Member");
        map.addAttribute("occupationList", occupationEnumSorting.getSortedList());

        return "staff-add";
    }

    @GetMapping("/delete/{id}")
    public String deleteStaffById(@PathVariable("id") Long id, Model model) {
        photoServiceStaff.deletePhoto(id);
        deleteStaffService.deleteStaff(id);
        return "redirect:/prison-management-system/staffs";
    }

    @GetMapping("/profile/{id}")
    public String staffProfileById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageName", "Staff Member Profile");
        Staff staff = getStaffService.findStaffById(id);
        model.addAttribute("staff", staff);
        model.addAttribute("occupationList", occupationEnumSorting.getSortedList());
        return "staff-profile";
    }

    @GetMapping("update/{id}")
    public String editStaffById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageName", "Edit Staff Member Profile");

        Staff staff = getStaffService.findStaffById(id);
        model.addAttribute("staff", staff);
        model.addAttribute("occupationList", occupationEnumSorting.getSortedList());
        return "staff-edit";
    }


    @GetMapping(value = "/staff-search")
    public String searchStaff(StaffSearch staffsearch, Model model) {
        model.addAttribute("pageName", "Staff Search");
        model.addAttribute("occupationList", occupationEnumSorting.getSortedList());
        return "staff-search";
    }

    @PostMapping(value = "/staff-search-result")
    public String getSearchedStaff(@Valid StaffSearch staffSearch, BindingResult bindingResult, Model model) {
        model.addAttribute("pageName", "Results matching your search criteria:");

        if (bindingResult.hasErrors()) {
            return "staff-search";
        }
        List<Staff> staffList = searchStaffService.searchStaff(staffSearch);
        if (staffList.size() == 0) {
            model.addAttribute("nothingFound", "There are no results matching your search criteria");
        }

        model.addAttribute("staffs", staffList);
        return "staff-search-result";
    }

    @PostMapping
    public String registerStaff(@Valid Staff staff,
                                BindingResult result, Model model,
                                @RequestParam("image") MultipartFile multipartFile) {

        if (result.hasErrors()) {
            staffAdd(model, staff);
            return "staff-add";
        }
        List<Staff> staffList = getStaffService.findAllStaff();
        for (Staff s : staffList) {
            if (staff.getPersonalCode().contains(s.getPersonalCode())) {
                model.addAttribute("errorFromController", "Staff member with personal code " + s.getPersonalCode() + " already exists");
                staffAdd(model, staff);
                return "staff-add";
            }
        }

        photoServiceStaff.uploadPhotoRegister(staff, multipartFile);
        createStaffService.registerStaff(staff);
        return staffStart(model);
    }

    @PostMapping("/update/{id}")
    public String updateStaff(@PathVariable("id") Long id,
                              @Valid Staff staff,
                              BindingResult result, Model model,
                              @RequestParam("image") MultipartFile multipartFile) {
        if (result.hasErrors()) {
            model.addAttribute("occupationList", occupationEnumSorting.getSortedList());
            return "staff-edit";
        }

        try {
            updateStaffService.updateStaff(id, staff);
        } catch (RuntimeException e) {
            if (e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                if ((e.getCause().getCause()).getLocalizedMessage().contains("Duplicate entry")) {
                    String errorMessage = ((e.getCause().getCause()).getLocalizedMessage().substring(15, 30));
                    model.addAttribute("errorFromController", "Staff with personal code " + errorMessage + " already exists");
                    model.addAttribute("occupationList", occupationEnumSorting.getSortedList());
                    return "staff-edit";
                }
            }
        }

        if (!multipartFile.isEmpty()) {
            photoServiceStaff.uploadPhoto(id, staff, multipartFile);

        }

        return "redirect:/prison-management-system/staffs/profile/" + id;
    }
}

