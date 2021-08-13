package com.prison.project.service.prisoner;

import com.prison.project.model.Prisoner;
import com.prison.project.model.Punishment;
import com.prison.project.repository.PrisonerRepository;
import com.prison.project.repository.PunishmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class UpdatePrisonerService {

    private final PrisonerRepository prisonerRepository;
    private final PunishmentRepository punishmentRepository;

    public Prisoner updatePrisoner (Long id, Prisoner updatePrisoner) {
        Prisoner existingPrisoner = prisonerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid prisoner id " + id));
        Punishment punishment = punishmentRepository.getById(updatePrisoner.getPunishmentId());
        existingPrisoner.setName(updatePrisoner.getName());
        existingPrisoner.setSurname(updatePrisoner.getSurname());
        existingPrisoner.setPersonalCode(updatePrisoner.getPersonalCode());
        existingPrisoner.setAddress(updatePrisoner.getAddress());
        existingPrisoner.setCrimesJson(updatePrisoner.getCrimesJson());
        existingPrisoner.setCrimes(updatePrisoner.getCrimes());
        existingPrisoner.setPunishment (punishment);
        existingPrisoner.setStartDate(updatePrisoner.getStartDate());


        return prisonerRepository.save(existingPrisoner);

    }

}
