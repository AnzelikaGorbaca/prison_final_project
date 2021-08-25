package com.prison.project.service.staff;

import com.prison.project.model.Staff;
import com.prison.project.repository.StaffRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class DeleteStaffServiceTest {

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private DeleteStaffService deleteStaffService;

    @Test
    void shouldDeleteStaff() {
        Staff sampleStaff = new Staff(1L, "Elvis", "Presley", "GUARD", "123456-12345",
                "+371212345678", "AddressConsistingOf10", "Moranto.jpg");

        doNothing().when(staffRepository).deleteById(1L);
        deleteStaffService.deleteStaff(1L);

        verify(staffRepository).deleteById(1L);
    }
}
