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

    public Staff registerStaff(Staff staff)  {

       staffRepository.save(staff);
        return staff;
    }
}
