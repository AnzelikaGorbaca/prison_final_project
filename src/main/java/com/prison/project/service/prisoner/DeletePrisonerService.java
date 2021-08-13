package com.prison.project.service.prisoner;

import com.prison.project.repository.CrimeRepository;
import com.prison.project.repository.PrisonerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class DeletePrisonerService {

    private final PrisonerRepository prisonerRepository;
    private final CrimeRepository crimeRepository;

    public void deletePrisoner (Long id) {
        var prisoner = prisonerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid prisoner id " + id));


        crimeRepository.deleteAll(prisoner.getCrimes());

        prisonerRepository.delete(prisoner);

    }
}
