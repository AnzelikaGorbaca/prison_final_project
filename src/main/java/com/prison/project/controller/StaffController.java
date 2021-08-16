package com.prison.project.controller;

import com.prison.project.model.Occupation;
import com.prison.project.model.PrisonCapacity;
import com.prison.project.model.Staff;
import com.prison.project.model.StaffSearch;
import com.prison.project.service.prisonCapacity.PrisonCapacityCheck;
import com.prison.project.service.staff.*;
import com.prison.project.utilities.FileUploadUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        map.addAttribute("occupationList", occupationList);

        return "staff-add";
    }

    @GetMapping("/delete/{id}")
    public String deleteStaffById(@PathVariable("id") Long id, Model model) {
        Path path = Paths.get("photos/" + "staff_" + id + "/" + getStaffService.findStaffById(id).getPhoto());
        FileUploadUtil.deleteFile(path);
        Path dir = Paths.get("photos/" + "staff_" + id);
        FileUploadUtil.deleteFile(dir);

        deleteStaffService.deleteStaff(id);
        return "redirect:/prison-management-system/staffs";
    }

    @GetMapping("/profile/{id}")
    public String staffProfileById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageName", "Staff Member Profile");

        Staff staff = getStaffService.findStaffById(id);
        model.addAttribute("staff", staff);

        List<Occupation> occupationList = occupationEnumSorting.getSortedList();
        model.addAttribute("occupationList", occupationList);

        return "staff-profile";
    }

    @GetMapping("update/{id}")
    public String editStaffById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageName", "Edit Staff Member Profile");

        Staff staff = getStaffService.findStaffById(id);
        model.addAttribute("staff", staff);

        List<Occupation> occupationList = occupationEnumSorting.getSortedList();
        model.addAttribute("occupationList", occupationList);

        return "staff-edit";
    }


    @GetMapping(value = "/staff-search")
    public String searchStaff(StaffSearch staffsearch, Model model) {
        model.addAttribute("pageName", "Staff Search");

        List<Occupation> occupationList = occupationEnumSorting.getSortedList();
        model.addAttribute("occupationList", occupationList);

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
            List<Occupation> occupationList = occupationEnumSorting.getSortedList();
            model.addAttribute("occupationList", occupationList);
            //  staffAdd(model,staff);
            return "staff-add";
        }
        List<Staff> staffList = getStaffService.findAllStaff();
        for (Staff s : staffList) {
            if (staff.getPersonalCode().contains(s.getPersonalCode())) {
                model.addAttribute("errorFromController", "Staff member with personal code " + s.getPersonalCode() + " already exists");
                List<Occupation> occupationList = occupationEnumSorting.getSortedList();
                model.addAttribute("occupationList", occupationList);
                //  staffAdd(model,staff);
                return "staff-add";
            }

        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        staff.setPhoto(fileName);
        Staff savedStaff = createStaffService.registerStaff(staff);
        String uploadDir = "photos/" + "staff_" + savedStaff.getId();
        try {
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } catch (IOException io) {
            staffAdd(model, staff); //probablu something else will be needed here

        }
        createStaffService.registerStaff(staff);
        return staffStart(model);
    }

    @PostMapping("/update/{id}")
    public String updateStaff(@PathVariable("id") Long id,
                              @Valid Staff staff,
                              @RequestParam("image") MultipartFile multipartFile,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Occupation> occupationList = occupationEnumSorting.getSortedList();
            model.addAttribute("occupationList", occupationList);
            return "staff-edit";
        }

        try {
            updateStaffService.updateStaff(id, staff);
        } catch (RuntimeException e) {
            if (e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                if ((e.getCause().getCause()).getLocalizedMessage().contains("Duplicate entry")) {
                    String errorMessage = ((e.getCause().getCause()).getLocalizedMessage().substring(15, 30));
                    model.addAttribute("errorFromController", "Staff with personal code " + errorMessage + " already exists");
                    List<Occupation> occupationList = occupationEnumSorting.getSortedList();
                    model.addAttribute("occupationList", occupationList);
                    return "staff-edit";
                }
            }
        }


        Staff savedStaff = updateStaffService.updateStaff(id, staff);
        if (!multipartFile.isEmpty())
            FileUploadUtil.deleteFile(Paths.get("photos/" + "staff_" + id + "/" + savedStaff.getPhoto()));
        try {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            String uploadDir = "photos/" + "staff_" + id;

            if (!fileName.isEmpty()) savedStaff.setPhoto(fileName);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
            updateStaffService.updateStaff(id, staff);
        } catch (RuntimeException | IOException e) {
            if (e.getCause().getCause() instanceof SQLIntegrityConstraintViolationException) {
                if ((e.getCause().getCause()).getLocalizedMessage().contains("Duplicate entry")) {

                    String errorMessage = ((e.getCause().getCause()).getLocalizedMessage().substring(15, 30));
                    model.addAttribute("errorFromController", "Staff member with personal code " + errorMessage + " already exists");
                    List<Occupation> occupationList = occupationEnumSorting.getSortedList();
                    model.addAttribute("occupationList", occupationList);
                    return "staff-edit";
                }
            }
        }
        return "redirect:/prison-management-system/staffs/profile/"+id;
    }
}
