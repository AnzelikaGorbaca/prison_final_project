package com.prison.project.service.staff;

import com.prison.project.exception.NotFoundException;
import com.prison.project.model.Staff;
import com.prison.project.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Service
@AllArgsConstructor
public class GetStaffService {
    private final StaffRepository staffRepository;

    public List<Staff> findAllStaff(){
       return staffRepository.findAll();
    }

    public Staff findStaffById (Long id){
        return staffRepository.findById(id).orElseThrow(() -> new NotFoundException("No Staff member with such id exists"));
    }

}
