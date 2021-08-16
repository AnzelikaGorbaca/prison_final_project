package com.prison.project.service.staff;

import com.prison.project.model.Occupation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class OccupationDropDownRestore {

    private final OccupationEnumSorting occupationEnumSorting;


    public void occupationDropDownList (Model model){
        List<Occupation> occupationList = occupationEnumSorting.getSortedList();
        model.addAttribute("occupationList", occupationList);
    }
}
