package com.prison.project.service.prisonCapacity;

import com.prison.project.exception.NotFoundException;
import com.prison.project.model.PrisonCapacity;
import com.prison.project.model.Prisoner;
import com.prison.project.repository.PrisonerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Transactional
@Service
@AllArgsConstructor
public class PrisonCapacityCheck {

    private final PrisonCapacity prisonCapacity;
    private final PrisonerRepository prisonerRepository;


    public Long getFreePlacesByDate(LocalDate localDate) {
        Long prisonerCount = prisonerRepository.countByEndDateGreaterThan(localDate);
        Long freeSpaces = prisonCapacity.getCapacity() - prisonerCount;
        if (freeSpaces > 0) {
            return freeSpaces;
        }
        return 0L;
    }

    public LocalDate getClosestDateWithFreePlaces(LocalDate localDate) throws NotFoundException {
        Optional<Prisoner> prisoner = prisonerRepository.findTopByEndDateGreaterThanOrderByEndDateAsc(localDate);
        if (prisoner.isPresent()) {
            return (prisoner.get().getEndDate()).plusDays(1);
        } else {
            throw new NotFoundException("There are no prisoners registered in the system");
        }
    }

    public Long getNumberOfFreePlacesInClosestDate(LocalDate localDate) {
        Long prisonerCount = prisonerRepository.countByEndDateGreaterThan(getClosestDateWithFreePlaces(LocalDate.now().minusDays(1)));
        Long freeSpaces = prisonCapacity.getCapacity() - prisonerCount;

        if (freeSpaces > 0) {
            return freeSpaces;
        }
        return 0L;
    }




}
