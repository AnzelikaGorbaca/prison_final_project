package com.prison.project.service.prisoner;

import com.prison.project.repository.PrisonerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class DeletePrisonerService {

    private final PrisonerRepository prisonerRepository;

    public void deletePrisoner(Long id) {
        prisonerRepository.deletePrisonerCrimes(id);
        prisonerRepository.deleteById(id);
    }
}
