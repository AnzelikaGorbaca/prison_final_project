package com.prison.project.repository;

import com.prison.project.model.Crime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CrimeRepository extends JpaRepository <Crime,Long> {
    Optional<Crime> findByCrimeDescription(String crimeDescription);
}
