package com.prison.project.service.prisoner;

import com.prison.project.model.Prisoner;
import com.prison.project.repository.PrisonerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@AllArgsConstructor
public class GetPrisonerService {

    private final PrisonerRepository prisonerRepository;
    private final StatusPrisonerService statusPrisonerService;

    public List<Prisoner> getAll() {
        return prisonerRepository.findAll();
    }

    public List<Prisoner> getAllWithStatus() {
        List<Prisoner> prisoners = prisonerRepository.findAllByOrderByIdDesc();
        statusPrisonerService.checkIfInPrisonAndSetStatus(prisoners);
        return prisoners;
    }

    public Prisoner getPrisonerById(Long id) {
        return prisonerRepository.getById(id);
    }

    public List<Prisoner> getTopPrisonersByImprisonmentMonths() {
        List<Prisoner> resultList = prisonerRepository.findTop10ByOrderByPunishment_ImprisonmentMonthsDesc();
        statusPrisonerService.checkIfInPrisonAndSetStatus(resultList);
        return resultList;
    }
}
