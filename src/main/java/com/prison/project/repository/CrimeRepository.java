package com.prison.project.repository;

import com.prison.project.model.Crime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CrimeRepository extends JpaRepository<Crime, Long> {
    Optional<Crime> findByCrimeDescription(String crimeDescription);

    @Query(value = "select crime_id from prisoner_crime where crime_id = :crime_id", nativeQuery = true)
    List<Long> getPrisonerCrimes(@Param("crime_id") Long crime_id);
}
