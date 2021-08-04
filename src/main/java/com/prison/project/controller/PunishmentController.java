package com.prison.project.controller;


import com.prison.project.model.Punishment;
import com.prison.project.service.punishment.CreatePunishmentService;
import com.prison.project.service.punishment.DeletePunishmentService;
import com.prison.project.service.punishment.GetPunishmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/prison-management-system/punishments")
public class PunishmentController {

    private final CreatePunishmentService createPunishmentService;
    private final DeletePunishmentService deletePunishmentService;
    private final GetPunishmentService getPunishmentService;

    @GetMapping
    public String punishmentIndex(Model model){
        model.addAttribute("pageName", "All Punishments");
        model.addAttribute("punishments", getPunishmentService.getAllPunishments());
        return "punishment-index";
    }

    @GetMapping("/punishment-add")
    public String addPunishment(Model map, Punishment punishment){
        map.addAttribute("pageName", "Add New Punishment");

        return "punishment-add";
    }

    @PostMapping
    public String registerPunishment(@Valid Punishment punishment, BindingResult result, Model model){
        if(result.hasErrors()) {
            return "punishment-add";
        }

        createPunishmentService.registerPunishment(punishment);
        return punishmentIndex(model);
    }

    @GetMapping("/delete/{id}")
    public String deletePunishment(@PathVariable("id") Long id, Model model){
        deletePunishmentService.deletePunishment(id);
        return punishmentIndex(model);
    }


}
