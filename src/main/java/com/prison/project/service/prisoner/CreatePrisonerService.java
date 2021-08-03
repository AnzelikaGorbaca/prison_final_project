package com.prison.project.service.prisoner;

import com.prison.project.model.Prisoner;
import com.prison.project.repository.PrisonerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class CreatePrisonerService {

    private final PrisonerRepository prisonerRepository;

    public Prisoner registerPrisoner (Prisoner prisoner) {
        prisonerRepository.save(prisoner);
        return prisoner;
    }

}
