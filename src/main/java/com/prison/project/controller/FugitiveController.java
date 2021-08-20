package com.prison.project.controller;

import com.prison.project.model.Fugitive;
import com.prison.project.service.fugitive.FugitiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/prison-management-system")
public class FugitiveController {

    private final FugitiveService fugitiveService;

    @GetMapping
    public String showFiveFugitives(Model model) {
        List<Fugitive> fugitives = fugitiveService.getFiveFugitiveList(8);

        Fugitive fugitive = fugitives.get(0);
        Fugitive fugitive1 = fugitives.get(1);
        Fugitive fugitive2 = fugitives.get(2);
        Fugitive fugitive3 = fugitives.get(3);
        Fugitive fugitive4 = fugitives.get(4);
        model.addAttribute("fugitive", fugitive);
        model.addAttribute("fugitive1", fugitive1);
        model.addAttribute("fugitive2", fugitive2);
        model.addAttribute("fugitive3", fugitive3);
        model.addAttribute("fugitive4", fugitive4);
        return "main-fbi";
    }
}
