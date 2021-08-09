package com.prison.project.service.prisoner;

import com.prison.project.model.Prisoner;
import com.prison.project.model.PrisonerSearch;
import com.prison.project.model.Staff;
import com.prison.project.model.StaffSearch;
import com.prison.project.repository.PrisonerRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.matchingAll;

@Transactional
@Service
@AllArgsConstructor
public class SearchPrisonerService {

    public final PrisonerRepository repository;

    public List<Prisoner> searchPrisoner(PrisonerSearch prisonerSearch) {
        Prisoner prisoner = new Prisoner();

        prisoner.setName(prisonerSearch.getName());
        prisoner.setSurname(prisonerSearch.getSurname());
        prisoner.setPersonalCode(prisonerSearch.getPersonalCode());
        prisoner.setAddress(prisonerSearch.getAddress());
        prisoner.setCrimes(prisonerSearch.getCrimes());
        prisoner.setPunishment(prisonerSearch.getPunishment());

        Example<Prisoner> prisonerExample = Example.of(prisoner, matchingAll().withIgnoreNullValues().withIgnoreCase());
        return repository.findAll(prisonerExample);
    }

}
