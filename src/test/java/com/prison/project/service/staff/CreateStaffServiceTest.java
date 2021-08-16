package com.prison.project.service.staff;


import com.prison.project.model.Staff;
import com.prison.project.repository.StaffRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateStaffServiceTest {

    @InjectMocks
    private CreateStaffService createStaffService;

    @Mock
    private StaffRepository staffRepository;


     @Test
      void shouldRegisterStaff() {
         Staff sampleStaff = new Staff(1L,"Elvis","Presley","GUARD","123456-12345",
                 "+371212345678","AddressConsistingOf10","Moranto.jpg");

         when(staffRepository.save(sampleStaff)).thenReturn(sampleStaff);

         Staff staff = createStaffService.registerStaff(sampleStaff);

         assertEquals(staff.getId(),sampleStaff.getId());
         assertEquals(staff.getName(),sampleStaff.getName());
         assertEquals(staff.getSurname(),sampleStaff.getSurname());
         assertEquals(staff.getOccupation(),sampleStaff.getOccupation());
         assertEquals(staff.getPersonalCode(),sampleStaff.getPersonalCode());
         assertEquals(staff.getPhoneNumber(),sampleStaff.getPhoneNumber());
         assertEquals(staff.getAddress(),sampleStaff.getAddress());
         assertEquals(staff.getPhoto(),sampleStaff.getPhoto());
         assertEquals(staff, sampleStaff);

         verify(staffRepository).save(sampleStaff);
     }
}
