package com.prison.project.repository;


import com.prison.project.model.Prisoner;
import com.prison.project.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StaffRepository extends JpaRepository<Staff, Long> {

    List<Staff> findAllByOrderByIdDesc();

}
