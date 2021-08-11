package com.prison.project.repository;

import com.prison.project.model.Prisoner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PrisonerRepository extends JpaRepository<Prisoner, Long> {


    Optional<Prisoner> findTopByEndDateGreaterThanOrderByEndDateAsc(LocalDate localDate);

    Long countByEndDateGreaterThan(LocalDate localDate);

    List<Prisoner> findTop10ByOrderByPunishment_ImprisonmentMonthsDesc();


}
