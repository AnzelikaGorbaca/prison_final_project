package com.prison.project.repository;

import com.prison.project.model.Punishment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PunishmentRepository extends JpaRepository<Punishment, Long> {

    List<Punishment> findAllByOrderByImprisonmentMonthsAsc();

    @Query(value = "select punishment_id from prisoner where punishment_id = :punishment_id", nativeQuery = true)
    List<Long> getPrisonerPunishment(@Param("punishment_id") Long punishment_id);
}
