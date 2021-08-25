package com.prison.project.service.prisoner;

import com.prison.project.model.Crime;
import com.prison.project.model.Prisoner;
import com.prison.project.model.Punishment;
import com.prison.project.repository.PrisonerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetPrisonerServiceTest {

    @InjectMocks
    private GetPrisonerService getPrisonerService;

    @Mock
    private PrisonerRepository repository;

    @Mock
    private StatusPrisonerService statusPrisonerService;

    private final Punishment punishment = new Punishment(1L, 5);

    private final List<Crime> crimes1 = Arrays.asList(new Crime(2L, "Murder"),
            new Crime(3L, "Robbery"),
            new Crime(4L, "Awful cook"));

    private final List<Crime> crimes2 = Arrays.asList(new Crime(2L, "Murder"),
            new Crime(3L, "Robbery"));

    private LocalDate getStartDate() {
        String start = "2021-08-13";
        return LocalDate.parse(start);
    }

    private LocalDate getEndDate() {
        String end = "2022-01-13";
        return LocalDate.parse(end);
    }

    @Nested
    class nestedClass {

        List<Prisoner> prisoners = Arrays.asList(new Prisoner(2L, "Janis", "Ozolins", "310856 - 10605",
                        "Rigas iela 4-5", getStartDate(), getEndDate(), "Janis.jpg",
                        true, "In Prison", crimes1, punishment, punishment.getId(), "Murder, Robbery, Awful Cook"),
                (new Prisoner(3L, "Peteris", "Zarins", "190665 - 10005",
                        "Rigas iela 4-5", getStartDate(), getEndDate(), "Peteris.jpg",
                        true, "In Prison", crimes2, punishment, punishment.getId(), "Murder, Robbery")));

        @BeforeEach
        private void setUp() {
            when(repository.findAll()).thenReturn(Arrays.asList(new Prisoner(2L, "Janis", "Ozolins", "310856 - 10605",
                            "Rigas iela 4-5", getStartDate(), getEndDate(), "Janis.jpg",
                            true, "In Prison", crimes1, punishment, punishment.getId(), "Murder, Robbery, Awful Cook"),
                    (new Prisoner(3L, "Peteris", "Zarins", "190665 - 10005",
                            "Rigas iela 4-5", getStartDate(), getEndDate(), "Peteris.jpg",
                            true, "In Prison", crimes2, punishment, punishment.getId(), "Murder, Robbery"))));
        }

        @Test
        void getAll() {

            List<Prisoner> prisoners = getPrisonerService.getAll();
            assertEquals(2, prisoners.size());
            assertEquals("Janis", prisoners.get(0).getName());
            assertNotEquals(prisoners.get(0), prisoners.get(1));

            verify(repository).findAll();
        }
    }

    @Test
    void getAllWithStatus() {

        List<Prisoner> prisoners = Arrays.asList(new Prisoner(2L, "Janis", "Ozolins", "310856 - 10605",
                        "Rigas iela 4-5", getStartDate(), getEndDate(), "Janis.jpg",
                        true, "In Prison", crimes1, punishment, punishment.getId(), "Murder, Robbery, Awful Cook"),
                (new Prisoner(3L, "Peteris", "Zarins", "190665 - 10005",
                        "Rigas iela 4-5", getStartDate(), getEndDate(), "Peteris.jpg",
                        true, "In Prison", crimes2, punishment, punishment.getId(), "Murder, Robbery")));

        doNothing().when(statusPrisonerService).checkIfInPrisonAndSetStatus(prisoners);
        when(repository.findAllByOrderByIdDesc()).thenReturn(prisoners);
        List<Prisoner> prisonersActual = getPrisonerService.getAllWithStatus();

        assertEquals(prisoners.get(0).getStatus(), prisonersActual.get(0).getStatus());

        verify(repository).findAllByOrderByIdDesc();
        verify(statusPrisonerService).checkIfInPrisonAndSetStatus(prisoners);
    }


    @Test
    void getPrisonerById() {
        Prisoner prisoner = new Prisoner(2L, "Janis", "Ozolins", "310856 - 10605",
                "Rigas iela 4-5", getStartDate(), getEndDate(), "Janis.jpg",
                true, "In Prison", crimes1, punishment, punishment.getId(), "Murder, Robbery, Awful Cook");
        when(repository.getById(2L)).thenReturn(prisoner);
        Prisoner prisonerActual = getPrisonerService.getPrisonerById(2L);

        assertEquals(prisoner, prisonerActual);
        assertEquals(prisoner.getId(), prisonerActual.getId());

        verify(repository).getById(2L);
    }

    @Test
    void getTopPrisonersByImprisonmentMonths() {
        Punishment punishment2 = new Punishment(2L, 10);
        List<Prisoner> prisoners = Arrays.asList(new Prisoner(2L, "Janis", "Ozolins", "310856 - 10605",
                        "Rigas iela 4-5", getStartDate(), getEndDate(), "Janis.jpg",
                        true, "In Prison", crimes1, punishment2, punishment2.getId(), "Murder, Robbery, Awful Cook"),
                (new Prisoner(3L, "Peteris", "Zarins", "190665 - 10005",
                        "Rigas iela 4-5", getStartDate(), getEndDate(), "Peteris.jpg",
                        true, "In Prison", crimes2, punishment, punishment.getId(), "Murder, Robbery")));

        when(repository.findTop10ByOrderByPunishment_ImprisonmentMonthsDesc()).thenReturn(prisoners);
        doNothing().when(statusPrisonerService).checkIfInPrisonAndSetStatus(prisoners);
        List<Prisoner> prisonersActual = getPrisonerService.getTopPrisonersByImprisonmentMonths();

        assertEquals(prisoners, prisonersActual);
        assertNotEquals(prisonersActual.get(0), prisonersActual.get(1));

        verify(repository).findTop10ByOrderByPunishment_ImprisonmentMonthsDesc();
        verify(statusPrisonerService).checkIfInPrisonAndSetStatus(prisoners);
    }
}