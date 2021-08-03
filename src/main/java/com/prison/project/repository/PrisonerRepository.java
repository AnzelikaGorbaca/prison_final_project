package com.prison.project.repository;

import com.prison.project.model.Prisoner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrisonerRepository extends JpaRepository<Prisoner, Long> {
}
