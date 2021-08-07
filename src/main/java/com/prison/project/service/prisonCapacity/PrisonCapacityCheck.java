package com.prison.project.service.prisonCapacity;

import com.prison.project.model.PrisonCapacity;
import com.prison.project.repository.PrisonerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Transactional
@Service
@AllArgsConstructor
public class PrisonCapacityCheck {

    private final PrisonCapacity prisonCapacity;
    private final PrisonerRepository prisonerRepository;


    public Long getFreePlacesNow (){
       return prisonCapacity.getCapacity().longValue()- prisonerRepository.count();
    }

    public Long getFreePlacesByDate(LocalDate localDate){
       // prisonerRepository.findAll()
        return null;

    }
}
