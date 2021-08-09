package com.prison.project.repository;

import com.prison.project.model.Prisoner;
import com.prison.project.model.Punishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrisonerRepository extends JpaRepository<Prisoner, Long> {


}
