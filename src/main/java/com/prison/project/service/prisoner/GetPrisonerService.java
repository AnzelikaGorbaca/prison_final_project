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

    public List<Prisoner> getAll (){
        return prisonerRepository.findAll();
    }
    public Prisoner getPrisonerById (Long id) {
        return prisonerRepository.getById(id);
    }
}
