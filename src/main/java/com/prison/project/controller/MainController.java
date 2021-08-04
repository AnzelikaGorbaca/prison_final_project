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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(value="/prison-management-system")
public class MainController {

    @GetMapping
    public String mainIndex(Model model){
        return "main-index";
    }


}
