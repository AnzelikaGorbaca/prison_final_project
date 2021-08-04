package com.prison.project.service.crime;

import com.prison.project.model.Crime;
import com.prison.project.repository.CrimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class UpdateCrimeService {

    private final CrimeRepository crimeRepository;

    public Crime updateCrime (Long id, Crime crime){
        Crime existingCrime = crimeRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Crime not found"));

        existingCrime.setCrimeDescription(crime.getCrimeDescription());
        return crimeRepository.save(existingCrime);
    }
}
