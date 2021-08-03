package com.prison.project.service.prisoner;

import com.prison.project.model.Prisoner;
import com.prison.project.repository.PrisonerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class UpdatePrisonerService {

    private final PrisonerRepository prisonerRepository;

    public Prisoner updatePrisoner (Long id, Prisoner updatePrisoner) {
        Prisoner existingPrisoner = prisonerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid prisoner id " + id));
        existingPrisoner.setName(updatePrisoner.getName());
        existingPrisoner.setSurname(updatePrisoner.getSurname());
        existingPrisoner.setPersonalCode(updatePrisoner.getPersonalCode());
        existingPrisoner.setAddress(updatePrisoner.getAddress());
//        existingPrisoner.setCrimes(updatePrisoner.getCrimes());
//        existingPrisoner.setPunishment (updatePrisoner.getPunishment());

        return prisonerRepository.save(existingPrisoner);

    }

}
