package com.prison.project.controller;

import com.prison.project.exception.BadRequestException;
import com.prison.project.model.Prisoner;
import com.prison.project.model.Staff;
import com.prison.project.service.staff.CreateStaffService;
import com.prison.project.service.staff.DeleteStaffService;
import com.prison.project.service.staff.GetStaffService;
import com.prison.project.service.staff.UpdateStaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.support.SQLErrorCodesFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/prison-management-system/staffs", produces = APPLICATION_JSON_VALUE)
public class StaffController {

    private final CreateStaffService createStaffService;
    private final DeleteStaffService deleteStaffService;
    private final GetStaffService getStaffService;
    private final UpdateStaffService updateStaffService;

    @GetMapping
    public String staffStart(Model model) {
        model.addAttribute("pageName", "All Staff");
        model.addAttribute("staffs", getStaffService.findAllStaff());
        return "staff-index";
    }

    @GetMapping("/staff-add")
    public String staffAdd(Model map, Staff staff) {
        map.addAttribute("pageName", "Add New Staff Member");
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

        return "staff-edit";

    }

    @PostMapping
    public String registerStaff(@Valid Staff staff, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "staff-add";
        }
        try {
            createStaffService.registerStaff(staff);
        } catch (Exception e) {
            if (e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                if (((SQLIntegrityConstraintViolationException) e.getCause().getCause()).getLocalizedMessage().contains("Duplicate entry")) { //SQL error code 1062
//                    throw new BadRequestException("MessageHERERRRRRR");                                                    //SQL state 2300

                    String errorMessage = (((SQLIntegrityConstraintViolationException) e.getCause().getCause()).getLocalizedMessage().substring(15,30));
                    model.addAttribute("pageName", "Staff member with personal code " +errorMessage+ " already exists");

                    return "staff-unique";
                }
            }
        }
        return staffStart(model);

         /*   if (result.hasErrors()) {
            return "staff-add";
        }
        try {
            createStaffService.registerStaff(staff);
        } catch (BadRequestException b) {
            return "staff-add";

        }

        return staffStart(model);*/

    }

        @PostMapping("/update/{id}")
        public String updateStaff (@PathVariable("id") Long id, @Valid Staff staff, BindingResult result, Model model){
            if (result.hasErrors()) {
                return "staff-edit";
            }
            updateStaffService.updateStaff(id, staff);
            return staffStart(model);
        }


    }
