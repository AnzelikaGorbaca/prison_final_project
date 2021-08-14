package com.prison.project.service.prisoner;

import com.prison.project.model.*;
import com.prison.project.repository.PrisonerRepository;
import com.prison.project.repository.PunishmentRepository;
import com.prison.project.service.crime.GetCrimeService;
import com.prison.project.service.punishment.GetPunishmentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.matchingAll;

@Transactional
@Service
@AllArgsConstructor
public class SearchPrisonerService {

    public final PrisonerRepository repository;
    public final GetPunishmentService punishmentService;
    public final GetCrimeService getCrimeService;
    public final GetPrisonerService getPrisonerService;
    public final StatusPrisonerService statusPrisonerService;

    public List<Prisoner> searchPrisoner(PrisonerSearch prisonerSearch) {
        Prisoner prisoner = new Prisoner();

        prisoner.setName(prisonerSearch.getName());
        prisoner.setSurname(prisonerSearch.getSurname());
        prisoner.setPersonalCode(prisonerSearch.getPersonalCode());
        prisoner.setAddress(prisonerSearch.getAddress());
        prisoner.setCrimes(getCrimesJson(prisonerSearch.getCrimesJson()));
        prisoner.setPunishment(getPunishmentByID(prisonerSearch.getPunishmentId()));
        prisoner.setStartDate(prisonerSearch.getStartDate());
        prisoner.setEndDate(prisonerSearch.getEndDate());

        String status = prisonerSearch.getStatus();
        prisoner.setInPrison(setBooleanInPrison(status));
        prisoner.setStatus(status);
        Example<Prisoner> prisonerExample = Example.of(prisoner, matchingAll().withIgnoreNullValues().withIgnoreCase());
        List<Prisoner> prisoners = repository.findAll(prisonerExample);
        statusPrisonerService.checkIfInPrisonAndSetStatus(prisoners);
        return prisoners;
    }

    private Boolean setBooleanInPrison(String status) {
        if (status == null) {
            return null;
        } else if (status.equals("Freed")) {
            return false;
        } else {
            return true;
        }
    }

    private Punishment getPunishmentByID(Long id) {
        if (id == null) {
            return null;
        }
        return punishmentService.getPunishmentById(id);
    }

    public List<Crime> getCrimesJson(String crimeJson) {
        if (crimeJson == null) {
            return null;
        }
        return getCrimeService.getCrimesJson(crimeJson);
    }
}

