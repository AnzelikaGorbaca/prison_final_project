package com.prison.project.repository;

import com.prison.project.model.Prisoner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PrisonerRepository extends JpaRepository<Prisoner, Long> {


    Optional<Prisoner> findTopByEndDateGreaterThanOrderByEndDateAsc(LocalDate localDate);

    Long countByEndDateGreaterThan(LocalDate localDate);

    List<Prisoner> findTop10ByOrderByPunishment_ImprisonmentMonthsDesc();

    @Modifying
    @Transactional
    @Query(value = "delete from prisoner_crime where prisoner_id = :prisonerId", nativeQuery = true)
    int deletePrisonerCrimes(@Param("prisonerId") Long prisonerId);

}
