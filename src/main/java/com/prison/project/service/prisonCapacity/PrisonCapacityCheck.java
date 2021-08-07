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


    public Long getFreePlacesNow() {
        Long freePlaces = prisonCapacity.getCapacity().longValue() - prisonerRepository.count();
       if (freePlaces > 0){
           return freePlaces;
       }
       return 0L;
    }

    public Long getFreePlacesByDate(LocalDate localDate) {
        // prisonerRepository.findAll()
        return null;

    }
}
