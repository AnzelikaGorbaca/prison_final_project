package com.prison.project.service.capacity;

import com.prison.project.exception.NotFoundException;
import com.prison.project.model.PrisonCapacity;
import com.prison.project.model.Prisoner;
import com.prison.project.repository.PrisonerRepository;
import com.prison.project.service.prisonCapacity.PrisonCapacityCheck;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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

        verify(prisonerRepository).countByEndDateGreaterThan(localDate);

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
    void shouldReturnClosestDateWithFreePlaces() {
        Prisoner samplePrisoner = new Prisoner();
        LocalDate localDate = LocalDate.now();

        when(prisonerRepository.findTopByEndDateGreaterThanOrderByEndDateAsc(localDate)).thenReturn(Optional.of(samplePrisoner));
        samplePrisoner.setEndDate(localDate);

        LocalDate closestDate = prisonCapacityCheck.getClosestDateWithFreePlaces();

        assertEquals(localDate.plusDays(1), closestDate);

        verify(prisonerRepository).findTopByEndDateGreaterThanOrderByEndDateAsc(localDate);
    }

    @Test
    void shouldThrowExceptionWhenNoPrisonersRegistered() {
        LocalDate localDate = LocalDate.now();

        when(prisonerRepository.findTopByEndDateGreaterThanOrderByEndDateAsc(localDate)).thenReturn(Optional.empty());

        try {

            LocalDate closestDate = prisonCapacityCheck.getClosestDateWithFreePlaces();
            fail();
        } catch (NotFoundException e) {
            assertEquals("There are no prisoners registered in the system", e.getMessage());

        }
        verify(prisonerRepository).findTopByEndDateGreaterThanOrderByEndDateAsc(localDate);
    }

    @Test
    void shouldReturnNumberOfFreePlacesOnClosestFreeDay() {
        LocalDate localDate = LocalDate.now();

        Prisoner samplePrisoner = new Prisoner();
        when(prisonerRepository.findTopByEndDateGreaterThanOrderByEndDateAsc(localDate)).thenReturn(Optional.of(samplePrisoner));
        samplePrisoner.setEndDate(localDate);

        when(prisonerRepository.countByEndDateGreaterThan(localDate)).thenReturn(90L);
        when(prisonCapacity.getCapacity()).thenReturn(100);


        Long freeSpaces = prisonCapacityCheck.getNumberOfFreePlacesInClosestDate();

        assertEquals(10L, freeSpaces);

        verify(prisonerRepository).findTopByEndDateGreaterThanOrderByEndDateAsc(localDate);
        verify(prisonerRepository).countByEndDateGreaterThan(localDate);
    }

    @Test
    void shouldReturnZeroIfFreePlacesLessThan() {
        LocalDate localDate = LocalDate.now();

        Prisoner samplePrisoner = new Prisoner();
        when(prisonerRepository.findTopByEndDateGreaterThanOrderByEndDateAsc(localDate)).thenReturn(Optional.of(samplePrisoner));
        samplePrisoner.setEndDate(localDate);

        when(prisonerRepository.countByEndDateGreaterThan(localDate)).thenReturn(100L);
        when(prisonCapacity.getCapacity()).thenReturn(90);

        Long freeSpaces = prisonCapacityCheck.getNumberOfFreePlacesInClosestDate();

        assertEquals(0L, freeSpaces);

        verify(prisonerRepository).findTopByEndDateGreaterThanOrderByEndDateAsc(localDate);
        verify(prisonerRepository).countByEndDateGreaterThan(localDate);
    }

    @Test
    void shouldThrowExceptionIfNoPrisonersWithEndDateFound() {
        LocalDate localDate = LocalDate.now();

        Prisoner samplePrisoner = new Prisoner();
        samplePrisoner.setEndDate(localDate);

        try {

            Long freeSpaces = prisonCapacityCheck.getNumberOfFreePlacesInClosestDate();
            fail();

        } catch (NotFoundException e) {
            assertEquals("There are no prisoners registered in the system", e.getMessage());
        }

        verify(prisonerRepository).findTopByEndDateGreaterThanOrderByEndDateAsc(localDate);
    }
}
