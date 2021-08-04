package com.prison.project.service.staff;

import com.prison.project.model.Staff;
import com.prison.project.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class DeleteStaffService {

    private final StaffRepository staffRepository;

    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }
}
