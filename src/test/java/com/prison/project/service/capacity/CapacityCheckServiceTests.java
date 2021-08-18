package com.prison.project.service.capacity;

import com.prison.project.model.PrisonCapacity;
import com.prison.project.model.Prisoner;
import com.prison.project.repository.PrisonerRepository;
import com.prison.project.service.prisonCapacity.PrisonCapacityCheck;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CapacityCheckServiceTests {
    @Mock
    private PrisonerRepository prisonerRepository;


    @InjectMocks
    private PrisonCapacityCheck prisonCapacityCheck;

    @Mock
    private PrisonCapacity prisonCapacity;



    @Test
    void shouldReturnFreeSpacesOnDistinctDate() {
        LocalDate localDate = LocalDate.now();

        when(prisonerRepository.countByEndDateGreaterThan(localDate)).thenReturn(90L);

        when(prisonCapacity.getCapacity()).thenReturn(100);

        Long freePlaces = prisonCapacityCheck.getFreePlacesByDate(localDate);

        assertEquals(10L, freePlaces);

    }

    @Test
    void shouldReturnZeroIfFreeSpacesLeesThanOne() {
        LocalDate localDate = LocalDate.now();

        when(prisonerRepository.countByEndDateGreaterThan(localDate)).thenReturn(100L);

        when(prisonCapacity.getCapacity()).thenReturn(100);

        Long freePlaces = prisonCapacityCheck.getFreePlacesByDate(localDate);

        assertEquals(0, freePlaces);

        verify(prisonerRepository).countByEndDateGreaterThan(localDate);

    }

    @Test
    void shouldReturnClosestDateWithFreePlaces(){
        Prisoner samplePrisoner = new Prisoner();
        LocalDate localDate = LocalDate.now();

        when(prisonerRepository.findTopByEndDateGreaterThanOrderByEndDateAsc(localDate)).thenReturn(Optional.of(samplePrisoner));
        samplePrisoner.setEndDate(localDate);

        LocalDate closestDate = prisonCapacityCheck.getClosestDateWithFreePlaces(localDate);

        assertEquals(localDate.plusDays(1),closestDate);
    }

    /*
    public LocalDate getClosestDateWithFreePlaces(LocalDate localDate) throws NotFoundException {
        Optional<Prisoner> prisoner = prisonerRepository.findTopByEndDateGreaterThanOrderByEndDateAsc(localDate);
        if (prisoner.isPresent()) {
            return (prisoner.get().getEndDate()).plusDays(1);
        } else {
            throw new NotFoundException("There are no prisoners registered in the system");
        }
    }
     */
    /*
      public Long getNumberOfFreePlacesInClosestDate(LocalDate localDate) {
        Long prisonerCount = prisonerRepository.countByEndDateGreaterThan(getClosestDateWithFreePlaces(LocalDate.now().minusDays(1)));
        Long freeSpaces = prisonCapacity.getCapacity() - prisonerCount;

        if (freeSpaces > 0) {
            return freeSpaces;
        }
        return 0L;
    }

     */






}
