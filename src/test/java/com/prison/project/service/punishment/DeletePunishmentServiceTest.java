package com.prison.project.service.punishment;

import com.prison.project.model.Punishment;
import com.prison.project.repository.PunishmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeletePunishmentServiceTest {
    @Mock
    private PunishmentRepository punishmentRepository;
    @InjectMocks
    private DeletePunishmentService deletePunishmentService;

    @Test
    void shouldDeletePunishment() {
        final Punishment punishment = new Punishment();
        punishment.setId(15L);
        ReflectionTestUtils.setField(punishment, "imprisonmentMonths", 12);

        doNothing().when(punishmentRepository).deleteById(15L);
        deletePunishmentService.deletePunishment(15L);

        verify(punishmentRepository).deleteById(15L);
        verify(punishmentRepository, Mockito.times(1)).deleteById(punishment.getId());
    }

}