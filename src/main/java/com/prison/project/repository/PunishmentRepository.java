package com.prison.project.repository;
import com.prison.project.model.Punishment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PunishmentRepository extends JpaRepository<Punishment,Long> {

    List<Punishment> findAllByOrderByImprisonmentMonthsAsc();
}
