package com.prison.project.service.punishment;

import com.prison.project.model.Punishment;
import com.prison.project.repository.PunishmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreatePunishmentServiceTest {

    @Mock
    private PunishmentRepository punishmentRepository;

    @InjectMocks
    private CreatePunishmentService createPunishmentService;


    @Test
    void shouldSavePunishment() {
        final Punishment punishment = new Punishment();
        punishment.setId(1L);
        ReflectionTestUtils.setField(punishment, "imprisonmentMonths", 12);

        when(punishmentRepository.save(punishment)).thenReturn(punishment);

        final Punishment actualPunishment = createPunishmentService.registerPunishment(punishment);

        assertThat(actualPunishment).isEqualTo(punishment);
        verify(punishmentRepository).save(punishment);


    }
}