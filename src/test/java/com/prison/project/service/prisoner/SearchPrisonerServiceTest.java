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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.domain.ExampleMatcher.matchingAll;

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
//    @Mock
//    PrisonerSearch prisonerSearch;


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

//        when(prisonerSearch.getPunishmentId()).thenReturn(1L);
//        when(prisonerSearch.getName()).thenReturn("Janis");
//        when(prisonerSearch.getSurname()).thenReturn("Berzins");
//        when(prisonerSearch.getAddress()).thenReturn("Ozolnieku iela 15-3");
//        when(prisonerSearch.getPersonalCode()).thenReturn("310856-10605");
//        when(prisonerSearch.getCrimesJson()).thenReturn("Robbery, Murder");
//        when(prisonerSearch.getCrimes()).thenReturn(crimes);
//        when(prisonerSearch.getPunishmentId()).thenReturn(1L);
//        when(prisonerSearch.getPunishment()).thenReturn(punishment);
//        when(prisonerSearch.getStartDate()).thenReturn(getStartDate());
//        when(prisonerSearch.getEndDate()).thenReturn(getEndDate());
//        when(prisonerSearch.getStatus()).thenReturn("In Prison");

        PrisonerSearch prisonerSearch = new PrisonerSearch ("Jannis", "Berzins", "310856-10605",
                "Ozolnieku iela 15-3", getStartDate(), getEndDate(), crimes, 1L, punishment, "Robbery, Murder",
                "In Prison");

        Prisoner prisoner = new Prisoner();
        prisoner.setName(prisonerSearch.getName());
        prisoner.setSurname(prisonerSearch.getSurname());
        prisoner.setPersonalCode(prisonerSearch.getPersonalCode());
        prisoner.setAddress(prisonerSearch.getAddress());
        prisoner.setCrimes(prisonerSearch.getCrimes());
        prisoner.setPunishment(prisonerSearch.getPunishment());
        prisoner.setStartDate(prisonerSearch.getStartDate());
        prisoner.setEndDate(prisonerSearch.getEndDate());
        prisoner.setPunishmentId(prisonerSearch.getPunishmentId());


        Example<Prisoner> prisonerExample = Example.of(prisoner, matchingAll().withIgnoreNullValues().withIgnoreCase());
        List<Prisoner> prisoners = Arrays.asList(new Prisoner(1L, "Jannis", "Berzins", "310856-10605",
                "Ozolnieku iela 15-3", getStartDate(), getEndDate(), "janis.jpg", null,
                null, crimes, punishment, 1L, "Robbery, Murder"));

        when(repository.findAll(prisonerExample)).thenReturn(prisoners);
        doNothing().when(statusPrisonerService).checkIfInPrisonAndSetStatus(prisoners);

        prisoners.get (0).setInPrison(true);
        prisoners.get (0).setStatus("In Prison");

        List<Prisoner> prisonersActual = searchPrisonerService.searchPrisoner(prisonerSearch);

        assertEquals(prisoners.size(), prisonersActual.size());
        assertEquals(prisoners.get(0), prisonersActual.get(0));

        verify(repository).findAll(prisonerExample);
        verify(statusPrisonerService).checkIfInPrisonAndSetStatus(prisoners);

        verify(prisonerSearch).getPunishmentId();
        verify(prisonerSearch).getName();
        verify(prisonerSearch).getSurname();
        verify(prisonerSearch).getAddress();
        verify(prisonerSearch).getPersonalCode();
        verify(prisonerSearch).getCrimes();
        verify(prisonerSearch).getPunishmentId();
        verify(prisonerSearch).getPunishment();
        verify(prisonerSearch).getStartDate();
        verify(prisonerSearch).getEndDate();
        verify(prisonerSearch).getStatus();


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