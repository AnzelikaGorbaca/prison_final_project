package com.prison.project.service.staff;

import com.prison.project.exception.BadRequestException;
import com.prison.project.model.Staff;
import com.prison.project.repository.StaffRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;


@Transactional
@Service
@AllArgsConstructor
public class CreateStaffService {

    private final StaffRepository staffRepository;

  /*  @Autowired
    private final GetStaffService getStaffService;*/

    public Staff registerStaff(Staff staff)  {


        /*List<Staff> staffList = getStaffService.findAllStaff();
        for (Staff s : staffList) {
            if (staff.getPersonalCode().contains(s.getPersonalCode())) {
                throw new BadRequestException("Staff member with such personal code already exists");
            }
        }*/


       staffRepository.save(staff);
        return staff;
    }
}
