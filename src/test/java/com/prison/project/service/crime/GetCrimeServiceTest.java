package com.prison.project.service.crime;

import com.prison.project.model.Crime;
import com.prison.project.repository.CrimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCrimeServiceTest {

    @InjectMocks
    private GetCrimeService getCrimeService;

    @Mock
    private CrimeRepository repository;

    @Test
    void getAllCrime() {
        when(repository.findAll()).thenReturn(
                Arrays.asList(new Crime(2L, "Murder"),
                        new Crime(3L, "Robbery"),
                        new Crime(4L, "Awful cook")));


        List<Crime> crimes = getCrimeService.getAllCrime();
        assertEquals(3, crimes.size());
        assertEquals("Murder", crimes.get(0).getCrimeDescription());
        assertTrue(crimes.contains(new Crime(3L, "Robbery")));

        verify(repository).findAll();
    }

    @Test
    void getCrimeById() {

        Crime crime = new Crime(24L, "Murder");
        when(repository.findById(24L))
                .thenReturn(Optional.of(crime));
        Crime findCrime = getCrimeService.getCrimeById(24L);
        assertEquals("Murder", findCrime.getCrimeDescription());
        verify(repository).findById(24L);
    }

    @Test
    void getCrimeByIdWhenCrimeIsNotFound() {
        when(repository.findById(24L))
                .thenReturn(Optional.empty());

        try {
            getCrimeService.getCrimeById(24L);
            fail();
        } catch (Exception e) {
            assertEquals("Crime not found", e.getMessage());
        }

        verify(repository).findById(24L);
    }

    @Test
    void getCrimeByDescription() {

        when(repository.findByCrimeDescription("Murder")).thenReturn(
                Optional.of(new Crime(2L, "Murder")));

        Crime crime = getCrimeService.getCrimeByDescription("Murder");

        assertEquals(crime.getCrimeDescription(), "Murder");
        verify(repository).findByCrimeDescription("Murder");


    }

    @Test
    void getCrimeByDescriptionWhenCrimeIsNotFound() {

        when(repository.findByCrimeDescription("Murder"))
                .thenReturn(Optional.empty());

        try {
            getCrimeService.getCrimeByDescription("Murder");
            fail();
        } catch (Exception e) {
            assertEquals("Crime not found", e.getMessage());
        }

        verify(repository).findByCrimeDescription("Murder");
    }



}