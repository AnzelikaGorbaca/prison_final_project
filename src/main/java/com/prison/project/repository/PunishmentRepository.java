package com.prison.project.repository;
import com.prison.project.model.Punishment;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PunishmentRepository extends JpaRepository<Punishment,Long> {
}
