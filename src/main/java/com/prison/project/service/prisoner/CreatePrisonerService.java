package com.prison.project.service.prisoner;

import com.prison.project.model.Prisoner;
import com.prison.project.model.Punishment;
import com.prison.project.repository.PrisonerRepository;
import com.prison.project.repository.PunishmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Transactional
@Service
@AllArgsConstructor
public class CreatePrisonerService {

    private final PrisonerRepository prisonerRepository;
    private final PunishmentRepository punishmentRepository;
    private final StatusPrisonerService statusPrisonerService;

    public Prisoner registerPrisoner (Prisoner prisoner) {
        Punishment punishment = punishmentRepository.getById(prisoner.getPunishmentId());
        prisoner.setPunishment(punishment);
        var endDate = prisoner.getStartDate().plusMonths(punishment.getImprisonmentMonths());
        prisoner.setEndDate(endDate);
        statusPrisonerService.checkIfInPrisonAndSetStatus(prisoner);
        prisonerRepository.save(prisoner);
        return prisoner;
    }



}
