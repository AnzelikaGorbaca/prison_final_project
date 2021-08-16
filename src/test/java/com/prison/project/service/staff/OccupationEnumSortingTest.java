package com.prison.project.service.staff;


import com.prison.project.model.Occupation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class OccupationEnumSortingTest {
    @InjectMocks
    private OccupationEnumSorting occupationEnumSorting;


    @Test
    void shouldSortEnumNaturalOrderAsc() {
        List<Occupation> enumValuesWithoutSort = List.of(Occupation.values());
        List<Occupation> enumValuesWithBASICSort = List.of(Occupation.values()).stream().sorted().collect(Collectors.toList());
        List<Occupation> sorted = occupationEnumSorting.getSortedList();

        List<Occupation> sortedAsc = Arrays.asList(Occupation.ACCOUNTANT, Occupation.ASSISTANT_MANAGEMENT, Occupation.DIRECTOR);

        assertNotEquals(enumValuesWithoutSort, sorted);
        assertNotEquals(enumValuesWithBASICSort,sorted);
        assertIterableEquals(sortedAsc, sorted.stream().limit(3).collect(Collectors.toList()));
    }


}
