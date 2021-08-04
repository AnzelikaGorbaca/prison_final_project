package com.prison.project.service.staff;

import com.prison.project.exception.BadRequestException;
import com.prison.project.model.Staff;
import com.prison.project.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class UpdateStaffService {
    private final StaffRepository staffRepository;

    public Staff updateStaff(Long id, Staff updatedStaff) {
        Staff existingStaff = staffRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Invalid staff id " + id));

        existingStaff.setName(updatedStaff.getName());
        existingStaff.setSurname(updatedStaff.getSurname());
        existingStaff.setOccupation(updatedStaff.getOccupation());
        existingStaff.setPersonalCode(updatedStaff.getPersonalCode());
        existingStaff.setPhoneNumber(updatedStaff.getPhoneNumber());
        existingStaff.setAddress(updatedStaff.getAddress());

        return staffRepository.save(existingStaff);

    }
}
