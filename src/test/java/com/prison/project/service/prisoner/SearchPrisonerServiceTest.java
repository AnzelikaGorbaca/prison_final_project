package com.prison.project.service.prisoner;

import com.prison.project.model.Crime;
import com.prison.project.model.Prisoner;
import com.prison.project.model.PrisonerSearch;
import com.prison.project.model.Punishment;
import com.prison.project.repository.PrisonerRepository;
import com.prison.project.service.crime.GetCrimeService;
import com.prison.project.service.punishment.GetPunishmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchPrisonerServiceTest {

    @InjectMocks
    SearchPrisonerService searchPrisonerService;

    @Mock
    PrisonerRepository repository;
    @Mock
    GetPunishmentService punishmentService;
    @Mock
    GetCrimeService getCrimeService;
    @Mock
    GetPrisonerService getPrisonerService;
    @Mock
    StatusPrisonerService statusPrisonerService;


    private LocalDate getStartDate() {
        String start = "2021-08-13";
        return LocalDate.parse(start);
    }

    private LocalDate getEndDate() {
        String end = "2022-01-13";
        return LocalDate.parse(end);
    }

    List<Crime> crimes = Arrays.asList(new Crime(1L, "Robbery"), new Crime(2L, "Murder"));
    Punishment punishment = new Punishment(1L, 5);

    @Test
    void searchPrisonerWhenAllDataArePassedInSearch() {
        PrisonerSearch prisonerSearch = new PrisonerSearch("Jannis", "Berzins", "310856-10605",
                "Ozolnieku iela 15-3", getStartDate(), getEndDate(), crimes, 1L, punishment, "Robbery, Murder",
                "In Prison");


        List<Prisoner> prisoners = List.of(new Prisoner(1L, "Jannis", "Berzins", "310856-10605",
                "Ozolnieku iela 15-3", getStartDate(), getEndDate(), "janis.jpg", null,
                null, crimes, punishment, 1L, "Robbery, Murder"));

        when(repository.findAll(isA(Example.class))).thenReturn(prisoners);
        doNothing().when(statusPrisonerService).checkIfInPrisonAndSetStatus(prisoners);


        List<Prisoner> prisonersActual = searchPrisonerService.searchPrisoner(prisonerSearch);

        assertEquals(prisoners.size(), prisonersActual.size());
        assertEquals(prisoners.get(0), prisonersActual.get(0));

        verify(repository).findAll(isA(Example.class));
        verify(statusPrisonerService).checkIfInPrisonAndSetStatus(prisoners);
    }

    @Test
    void getCrimesJson() {

        String crimeJsonNull = null;
        String crimeJson = "Murder";

        List<Crime> crimes = Arrays.asList(new Crime(1L, "Murder"));
        when(getCrimeService.getCrimesJson(crimeJson)).thenReturn(crimes);
        List<Crime> crimeJsonWhenNull = searchPrisonerService.getCrimesJson(crimeJsonNull);
        List<Crime> crimeJsonWhenIsValue = searchPrisonerService.getCrimesJson(crimeJson);

        assertNull(crimeJsonWhenNull);
        assertEquals(crimes, crimeJsonWhenIsValue);

        verify(getCrimeService).getCrimesJson(crimeJson);
    }

    @Test
    void setBooleanInPrison() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method method = SearchPrisonerService.class
                .getDeclaredMethod("setBooleanInPrison", String.class);
        method.setAccessible(true);

        String statusNull = null;
        String statusInPrison = "In Prison";
        String statusFreed = "Freed";

        Boolean statusNullResult = (Boolean) method.invoke(searchPrisonerService, statusNull);
        Boolean statusInPrisonResult = (Boolean) method.invoke(searchPrisonerService, statusInPrison);
        Boolean statusFreedResult = (Boolean) method.invoke(searchPrisonerService, statusFreed);

        assertNull(statusNullResult);
        assertTrue(statusInPrisonResult);
        assertFalse(statusFreedResult);
    }

    @Test
    void getPunishmentByID() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Method method = SearchPrisonerService.class
                .getDeclaredMethod("getPunishmentByID", Long.class);
        method.setAccessible(true);

        Long idNull = null;
        Long id = 24L;
        Punishment punishment = new Punishment(24L, 5);
        when(punishmentService.getPunishmentById(24L)).thenReturn(punishment);

        Punishment whenPunishmentNull = (Punishment) method.invoke(searchPrisonerService, idNull);
        Punishment punishmentResult = (Punishment) method.invoke(searchPrisonerService, id);

        assertNull(whenPunishmentNull);
        assertEquals(punishment, punishmentResult);

        verify(punishmentService).getPunishmentById(24L);
    }
}