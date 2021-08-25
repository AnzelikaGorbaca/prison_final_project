package com.prison.project.service.prisoner;

import com.prison.project.model.Crime;
import com.prison.project.model.Prisoner;
import com.prison.project.model.Punishment;
import com.prison.project.repository.PrisonerRepository;
import com.prison.project.repository.PunishmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePrisonerServiceTest {

    @InjectMocks
    private CreatePrisonerService createPrisonerService;

    @Mock
    private PrisonerRepository prisonerRepository;
    @Mock
    private PunishmentRepository punishmentRepository;
    @Mock
    private StatusPrisonerService statusPrisonerService;
    @Mock
    private Punishment punishment;

    @BeforeEach
    void setUp() {
        when(punishment.getId()).thenReturn(1L);
    }

    private final List<Crime> crimes = Arrays.asList(new Crime(2L, "Murder"),
            new Crime(3L, "Robbery"),
            new Crime(4L, "Awful cook"));

    private LocalDate getStartDate() {
        String start = "2021-08-13";
        return LocalDate.parse(start);
    }

    private LocalDate getEndDate() {
        String end = "2022-01-13";
        return LocalDate.parse(end);
    }


    @Test
    void registerPrisoner() {
        String start = "2021-08-13";
        String end = "2022-01-13";
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);

        Prisoner samplePrisoner = new Prisoner(2L, "Janis", "Ozolins", "310856 - 10605",
                "Rigas iela 4-5", getStartDate(), null, "Change Color.jpg",
                null, "In Prison", crimes, null, punishment.getId(), "Murder, Robbery, Awful Cook");

        samplePrisoner.setEndDate(getEndDate());
        when(punishmentRepository.getById(samplePrisoner.getPunishmentId()))
                .thenReturn(new Punishment(1L, 5));
        samplePrisoner.setPunishment(punishment);

        doNothing().when(statusPrisonerService).checkIfInPrisonAndSetStatus(samplePrisoner);

        samplePrisoner.setStatus("In Prison");
        samplePrisoner.setInPrison(true);

        when(prisonerRepository.save(samplePrisoner)).thenReturn(samplePrisoner);

        Prisoner prisoner = createPrisonerService.registerPrisoner(samplePrisoner);

        assertEquals(prisoner.getAddress(), samplePrisoner.getAddress());
        assertEquals(prisoner.getInPrison(), samplePrisoner.getInPrison());
        assertEquals(prisoner.getPunishment(), samplePrisoner.getPunishment());
        assertEquals(prisoner, samplePrisoner);

        verify(punishmentRepository).getById(samplePrisoner.getPunishmentId());
        verify(statusPrisonerService).checkIfInPrisonAndSetStatus(samplePrisoner);
        verify(prisonerRepository).save(samplePrisoner);
        verify(punishment).getId();
    }

    @Test
    void calculateEndDate() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        when(punishment.getImprisonmentMonths()).thenReturn(5);
        Method method = CreatePrisonerService.class
                .getDeclaredMethod("calculateEndDate", Prisoner.class, Punishment.class);
        method.setAccessible(true);

        Prisoner samplePrisoner = new Prisoner(2L, "Janis", "Ozolins", "310856 - 10605",
                "Rigas iela 4-5", getStartDate(), null, "Change Color.jpg",
                null, "In Prison", crimes, punishment, punishment.getId(), "Murder, Robbery, Awful Cook");

        LocalDate result = (LocalDate) method.invoke(createPrisonerService, samplePrisoner, punishment);
        assertEquals(result, getEndDate());
        verify(punishment).getId();
        verify(punishment).getImprisonmentMonths();
    }

}
