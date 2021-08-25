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
import java.util.List;

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
        model.addAttribute("punishments", getPunishmentService.getAllPunishmentsOrderedAsc());
        return "punishment-index";
    }

    @GetMapping("/punishment-add")
    public String addPunishment(Model map, Punishment punishment){
        return "punishment-add";
    }

    @PostMapping
    public String registerPunishment(@Valid Punishment punishment, BindingResult result, Model model){
        if(result.hasErrors()) {
            return "punishment-add";
        }

        if (punishment.getImprisonmentMonths()==0){
            model.addAttribute("punishmentNot0", "Imprisonment cannot be shorter than 1 month");
            return "punishment-add";
        }

        List<Punishment> punishmentList = getPunishmentService.getAllPunishments();
        for (Punishment p: punishmentList){
            if (p.getImprisonmentMonths()==punishment.getImprisonmentMonths()){
                model.addAttribute("punishmentError", "Punishment " + p.getImprisonmentMonths() + " already exists in the system");
                return "punishment-add";
            }
        }

        createPunishmentService.registerPunishment(punishment);
        return punishmentIndex(model);
    }

    @GetMapping("/delete/{id}")
    public String deletePunishment(@PathVariable("id") Long id, Model model){
        List<Long> punishmentList = deletePunishmentService.getPunishmentPrisoner(id);
        if (!punishmentList.isEmpty()) {
            model.addAttribute("errorFromController", "Can't delete punishment with id: " + id + ". It is used for prisoner data!");
        }
        deletePunishmentService.deletePunishment(id);
        return punishmentIndex(model);
    }


}
