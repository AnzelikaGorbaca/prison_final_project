package com.prison.project.repository;

import com.prison.project.model.Crime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrimeRepository extends JpaRepository <Crime,Long> {
}
