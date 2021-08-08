package com.prison.project.service.crime;

import com.prison.project.model.Crime;
import com.prison.project.repository.CrimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateCrimeServiceTest {

    @InjectMocks
    private CreateCrimeService createCrimeService;

    @Mock
    private CrimeRepository repository;


    @Test
    void registerCrime() {

        Crime sampleCrime = new Crime(2L, "Murder");
        when(repository.save(sampleCrime)).thenReturn(sampleCrime);

        Crime crime = createCrimeService.registerCrime(sampleCrime);
        assertEquals(crime.getCrimeDescription(), sampleCrime.getCrimeDescription());
        assertEquals(crime.getId(), sampleCrime.getId());
        assertEquals(crime, sampleCrime);

        verify(repository).save(sampleCrime);
    }
}