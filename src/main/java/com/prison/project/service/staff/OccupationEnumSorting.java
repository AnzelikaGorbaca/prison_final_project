package com.prison.project.service.staff;

import com.prison.project.model.Occupation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Transactional
@Service
@AllArgsConstructor
public class OccupationEnumSorting {

    public List<Occupation> getSortedList() {

       List<Occupation> occupationList = new ArrayList<>(List.of(Occupation.values()));
       occupationList.sort(new Comparator<Occupation>() {
        @Override
            public int compare(Occupation o1, Occupation o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });

    return occupationList;
    }
}
