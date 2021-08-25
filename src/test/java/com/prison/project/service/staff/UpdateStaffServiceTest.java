package com.prison.project.service.staff;

import com.prison.project.exception.BadRequestException;
import com.prison.project.model.Staff;
import com.prison.project.repository.StaffRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateStaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private UpdateStaffService updateStaffService;

    @Test
    void shouldReturnUpdatedStaff() {
        Staff existing = new Staff(1L, "Elvis", "Presley", "GUARD", "123456-12345",
                "+371212345678", "AddressConsistingOf10", "Moranto.jpg");
        Staff afterUpdate = new Staff(1L, "John", "Walker", "ACCOUNTANT", "123456-00000",
                "+371212345677", "AddressConsistingOf11", "Moranto1.jpg");

        when(staffRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(staffRepository.save(existing)).thenReturn(existing);

        Staff updatedStaff = updateStaffService.updateStaff(1L, afterUpdate);

        assertEquals(updatedStaff, existing);
        assertNotNull(updatedStaff);

        verify(staffRepository).findById(1L);
        verify(staffRepository).save(existing);
    }

    @Test
    void shouldThrowExceptionIfNoStaffByIdFound() {
        Staff existing = new Staff(1L, "Elvis", "Presley", "GUARD", "123456-12345",
                "+371212345678", "AddressConsistingOf10", "Moranto.jpg");
        Long id = 1L;
        when(staffRepository.findById(1L)).thenReturn(Optional.empty());
        try {
            updateStaffService.updateStaff(1L, existing);
            fail();
        } catch (BadRequestException e) {
            assertEquals("Invalid staff id " + id, e.getMessage());

        }
        verify(staffRepository).findById(1L);
    }
}

