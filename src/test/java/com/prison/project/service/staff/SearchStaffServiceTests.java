package com.prison.project.service.staff;

import com.prison.project.model.Staff;
import com.prison.project.model.StaffSearch;
import com.prison.project.repository.StaffRepository;
import org.springframework.data.domain.Example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;

@ExtendWith(MockitoExtension.class)
public class SearchStaffServiceTests {

    @Mock
    private StaffRepository staffRepository;
    @InjectMocks
    private SearchStaffService searchStaffService;

    @Test
    void shouldReturnSearchedStaff(){
        List<Staff> sampleList = Arrays.asList(new Staff(1L,"Elvis","Presley","GUARD","123456-12345",
                        "+371212345678","AddressConsistingOf10","Moranto.jpg"),
                new Staff(2L,"John","Walker","ACCOUNTANT","123456-00000",
                        "+371212345677","AddressConsistingOf11","Moranto1.jpg"));
        StaffSearch staffSearch = new StaffSearch("Elvis","Presley","GUARD","123456-12345",
                "+371212345678","AddressConsistingOf10");

        when(staffRepository.findAll(isA(Example.class))).thenReturn(sampleList);

        List<Staff> foundStaff = searchStaffService.searchStaff(staffSearch);

        assertEquals(staffSearch.getName(), foundStaff.get(0).getName());
        assertEquals(staffSearch.getSurname(), foundStaff.get(0).getSurname());
        assertEquals(staffSearch.getAddress(),foundStaff.get(0).getAddress());
        assertEquals(staffSearch.getOccupation(),foundStaff.get(0).getOccupation());
        assertEquals(staffSearch.getPhoneNumber(),foundStaff.get(0).getPhoneNumber());
        assertEquals(staffSearch.getPersonalCode(),foundStaff.get(0).getPersonalCode());

        assertNotEquals(staffSearch.getName(), foundStaff.get(1).getName());
        assertNotEquals(staffSearch.getSurname(), foundStaff.get(1).getSurname());
        assertNotEquals(staffSearch.getAddress(),foundStaff.get(1).getAddress());
        assertNotEquals(staffSearch.getOccupation(),foundStaff.get(1).getOccupation());
        assertNotEquals(staffSearch.getPhoneNumber(),foundStaff.get(1).getPhoneNumber());
        assertNotEquals(staffSearch.getPersonalCode(),foundStaff.get(1).getPersonalCode());


        verify(staffRepository).findAll(isA(Example.class));
    }

    @Test
    void shouldReturnAllStaffIfNothingMatchesSearch(){
        List<Staff> sampleList = Arrays.asList(new Staff(1L,"Elvis","Presley","GUARD","123456-12345",
                        "+371212345678","AddressConsistingOf10","Moranto.jpg"),
                new Staff(2L,"John","Walker","ACCOUNTANT","123456-00000",
                        "+371212345677","AddressConsistingOf11","Moranto1.jpg"));
        StaffSearch staffSearch = new StaffSearch("Elvis1","Presley1","GUARD1","123456-12341",
                "+371212345671","AddressConsistingOf11");

        when(staffRepository.findAll(isA(Example.class))).thenReturn(sampleList);

        List<Staff> foundStaff = searchStaffService.searchStaff(staffSearch);

        assertEquals(sampleList.size(),foundStaff.size());


        verify(staffRepository).findAll(isA(Example.class));
    }

    @Test
    void shouldReturnSearchedStaffIfOnlyOneSearchCriteriaMatches(){
        List<Staff> sampleList = Arrays.asList(new Staff(1L,"Elvis","Presley","GUARD","123456-12345",
                        "+371212345678","AddressConsistingOf10","Moranto.jpg"),
                new Staff(2L,"John","Walker","ACCOUNTANT","123456-00000",
                        "+371212345677","AddressConsistingOf11","Moranto1.jpg"));
        StaffSearch staffSearch = new StaffSearch("Elvis","Presley1","GUARD1","123456-12341",
                "+371212345671","AddressConsistingOf11");

        when(staffRepository.findAll(isA(Example.class))).thenReturn(sampleList);

        List<Staff> foundStaff = searchStaffService.searchStaff(staffSearch);

        assertEquals(staffSearch.getName(),foundStaff.get(0).getName());
        assertNotEquals(staffSearch.getName(), foundStaff.get(1).getName());

        assertNotEquals(staffSearch.getSurname(), foundStaff.get(0).getSurname());
        assertNotEquals(staffSearch.getAddress(),foundStaff.get(0).getAddress());
        assertNotEquals(staffSearch.getOccupation(),foundStaff.get(0).getOccupation());
        assertNotEquals(staffSearch.getPhoneNumber(),foundStaff.get(0).getPhoneNumber());
        assertNotEquals(staffSearch.getPersonalCode(),foundStaff.get(0).getPersonalCode());


        verify(staffRepository).findAll(isA(Example.class));
    }

}
