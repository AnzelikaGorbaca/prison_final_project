package com.prison.project.service.staff;

import com.prison.project.exception.NotFoundException;
import com.prison.project.model.Staff;
import com.prison.project.repository.StaffRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetStaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private GetStaffService getStaffService;


    @Test
    void shouldReturnAllStaffInReverseOrder() {
        List<Staff> sampleList = Arrays.asList(new Staff(1L, "Elvis", "Presley", "GUARD", "123456-12345",
                        "+371212345678", "AddressConsistingOf10", "Moranto.jpg"),
                new Staff(2L, "John", "Walker", "ACCOUNTANT", "123456-00000",
                        "+371212345677", "AddressConsistingOf11", "Moranto1.jpg"));
        Collections.reverse(sampleList);

        when(staffRepository.findAllByOrderByIdDesc()).thenReturn(sampleList);

        List<Staff> staff = getStaffService.findAllStaff();

        assertEquals(2, staff.size());
        assertEquals("John", staff.get(0).getName());
        assertEquals("Elvis", staff.get(1).getName());
        assertIterableEquals(staff, sampleList);

        verify(staffRepository).findAllByOrderByIdDesc();
    }

    @Test
    void shouldReturnById() {
        Staff sampleStaff = new Staff(1L, "Elvis", "Presley", "GUARD", "123456-12345",
                "+371212345678", "AddressConsistingOf10", "Moranto.jpg");
        when(staffRepository.findById(1L)).thenReturn(Optional.of(sampleStaff));

        Staff findStaff = getStaffService.findStaffById(1L);

        assertEquals(sampleStaff, findStaff);
        verify(staffRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionIfNothingToReturnById() {
        when(staffRepository.findById(any())).thenReturn(Optional.empty());
        try {
            getStaffService.findStaffById(any());
            fail();
        } catch (NotFoundException e) {
            assertEquals("No Staff member with such id exists", e.getMessage());
        }

        verify(staffRepository).findById(any());
    }
}




