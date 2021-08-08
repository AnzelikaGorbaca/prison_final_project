package com.prison.project.service.crime;

import com.prison.project.model.Crime;
import com.prison.project.repository.CrimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateCrimeServiceTest {

    @InjectMocks
    private UpdateCrimeService updateCrimeService;

    @Mock
    private CrimeRepository repository;

    @Test
    void updateCrime() {
        long id = 24L;
        Crime old = new Crime(id, "adasds");
        Crime newUpdate = new Crime(id, "Murder");

        when(repository.findById(id)).thenReturn(Optional.of(old));
        old.setCrimeDescription(newUpdate.getCrimeDescription());

        when(repository.save(old)).thenReturn(old);

        Crime updatedCrime = updateCrimeService.updateCrime(id, newUpdate);

        assertEquals(updatedCrime, old);
        assertNotNull(updatedCrime);

        verify(repository).save(old);
        verify(repository).findById(id);

    }


    @Test
    void updateCrimeWhenNoCrimeFound() {
        long id = 24L;
        Crime newUpdate = new Crime(id, "Murder");

        when(repository.findById(24L))
                .thenReturn(Optional.empty());

        try {
            updateCrimeService.updateCrime(24L, newUpdate);
            fail();
        } catch (Exception e) {
            assertEquals("Crime not found", e.getMessage());
        }

        verify(repository).findById(24L);
    }
}