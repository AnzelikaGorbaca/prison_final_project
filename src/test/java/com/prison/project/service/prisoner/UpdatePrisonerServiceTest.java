package com.prison.project.service.prisoner;

import com.prison.project.model.Crime;
import com.prison.project.model.Prisoner;
import com.prison.project.model.Punishment;
import com.prison.project.repository.PrisonerRepository;
import com.prison.project.repository.PunishmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdatePrisonerServiceTest {
    @InjectMocks
    UpdatePrisonerService updatePrisonerService;

    @Mock
    PrisonerRepository prisonerRepository;
    @Mock
    PunishmentRepository punishmentRepository;


    String start = "2021-08-13";
    LocalDate startDate = LocalDate.parse(start);

    String end = "2022-01-13";
    LocalDate endDate = LocalDate.parse(end);


    List<Crime> crimes = Arrays.asList(new Crime(1L, "Robbery"), new Crime(2L, "Murder"));
    List<Crime> newCrimes = Arrays.asList(crimes.get(0));
    Punishment oldPunishment = new Punishment(2L, 10);
    Punishment newPunishment = new Punishment(1L, 5);

    Prisoner updatePrisoner = new Prisoner(2L, "Karlis", "Upitis", "310855-10605",
            "Dzirnavu iela 15-3", startDate, endDate, "karlis.jpg", false,
            "Freed", newCrimes, newPunishment, 1L, "Robbery");

    @Test
    void updatePrisonerWhenIsPrisoner() {

        when(punishmentRepository.getById(1L)).thenReturn(newPunishment);


        Prisoner existingPrisoner = new Prisoner(2L, "Jannis", "Berzins", "310856-10605",
                "Ozolnieku iela 15-3", startDate, endDate, "janis.jpg", true,
                "In Prison", crimes, oldPunishment, 2L, "Robbery,Murder");


        when(prisonerRepository.findById(2L)).thenReturn(java.util.Optional.of(existingPrisoner));
        when(prisonerRepository.save(existingPrisoner)).thenReturn(existingPrisoner);
        Prisoner updatedPrisoner = updatePrisonerService.updatePrisoner(2L, updatePrisoner);

        assertEquals(existingPrisoner,updatedPrisoner);
        assertEquals(existingPrisoner.getPunishment(),updatedPrisoner.getPunishment());
        assertEquals(existingPrisoner.getPhoto(), updatedPrisoner.getPhoto());
        assertEquals(1L, updatedPrisoner.getPunishmentId());

        verify(prisonerRepository).findById(2L);
        verify(punishmentRepository).getById(1L);
        verify(prisonerRepository).save(existingPrisoner);

    }

    @Test
    void updatePrisonerWheIsNoPrisoner() {

        Long id = 2L;
        when(prisonerRepository.findById(id)).thenReturn(Optional.empty());

        try {
            updatePrisonerService.updatePrisoner(id, updatePrisoner);
            fail();
        } catch (Exception e) {
            assertEquals("Invalid prisoner id " + id, e.getMessage());
        }

        verify(prisonerRepository).findById(id);

    }
}