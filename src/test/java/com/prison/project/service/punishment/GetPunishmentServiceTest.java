package com.prison.project.service.punishment;

import com.prison.project.model.Punishment;
import com.prison.project.repository.PunishmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetPunishmentServiceTest {

    @Mock
    private PunishmentRepository punishmentRepository;

    @InjectMocks
    private GetPunishmentService getPunishmentService;



    @Test
    void shouldReturnAllPunishments() {
        final Punishment punishment1 = new Punishment();
        punishment1.setId(15L);
        ReflectionTestUtils.setField(punishment1, "imprisonmentMonths", 12);

        final Punishment punishment2 = new Punishment();
        punishment2.setId(16L);
        ReflectionTestUtils.setField(punishment2, "imprisonmentMonths", 10);

        when(punishmentRepository.findAll()).thenReturn(
                Arrays.asList(punishment1, punishment2));

        List<Punishment> actualPunishments = getPunishmentService.getAllPunishments();

        assertNotNull(actualPunishments);
        assertEquals(2, actualPunishments.size());
        assertEquals(12, actualPunishments.get(0).getImprisonmentMonths());
        assertTrue(actualPunishments.contains(punishment2));

        verify(punishmentRepository).findAll();
    }

    @Test
    void shouldFindPunishmentById() {
        Long expectedId = 999L;
        final Punishment punishment = new Punishment();
        punishment.setId(expectedId);
        ReflectionTestUtils.setField(punishment, "imprisonmentMonths", 12);

        when(punishmentRepository.findById(999L))
                .thenReturn(Optional.of(punishment));

        Punishment foundPunishment = getPunishmentService.getPunishmentById(999L);

        assertEquals(expectedId,foundPunishment.getId());
        assertEquals(12, foundPunishment.getImprisonmentMonths());
        verify(punishmentRepository).findById(expectedId);
    }


    @Test
    void shouldThrowExceptionWhenIdNotFound() {
        Long expectedId = 50L;
        when(punishmentRepository.findById(expectedId))
                .thenReturn(Optional.empty());

       try {
           getPunishmentService.getPunishmentById(expectedId);
           fail();
       } catch(Exception e) {
           assertEquals(String.format("Punishment with id "+ expectedId + " does not exist"), e.getMessage());
       }

       verify(punishmentRepository).findById(expectedId);
    }

    @Test
    void shouldReturnAllPunishmentsAscByImprisonmentMonths() {
        final Punishment punishment1 = new Punishment();
        ReflectionTestUtils.setField(punishment1, "imprisonmentMonths", 600);

        final Punishment punishment2 = new Punishment();
        ReflectionTestUtils.setField(punishment2, "imprisonmentMonths", 100);

        final Punishment punishment3 = new Punishment();
        ReflectionTestUtils.setField(punishment3, "imprisonmentMonths", 200);

        final Punishment punishment4 = new Punishment();
        ReflectionTestUtils.setField(punishment4, "imprisonmentMonths", 500);

        when(punishmentRepository.findAllByOrderByImprisonmentMonthsAsc()).thenReturn(
                Arrays.asList(punishment1, punishment4, punishment3, punishment2));

        List<Punishment> actualPunishments = getPunishmentService.getAllPunishmentsOrderedAsc();

        assertNotNull(actualPunishments);
        assertEquals(4, actualPunishments.size());
        assertEquals(600, actualPunishments.get(0).getImprisonmentMonths());
        assertEquals(200, actualPunishments.get(2).getImprisonmentMonths());
        assertNotEquals(500, actualPunishments.get(3).getImprisonmentMonths());
        assertTrue(actualPunishments.contains(punishment2));

        verify(punishmentRepository).findAllByOrderByImprisonmentMonthsAsc();
    }

}