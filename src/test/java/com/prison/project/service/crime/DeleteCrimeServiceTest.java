package com.prison.project.service.crime;

import com.prison.project.model.Crime;
import com.prison.project.repository.CrimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteCrimeServiceTest {

    @InjectMocks
    private DeleteCrimeService deleteCrimeService;

    @Mock
    private CrimeRepository repository;

    @Test
    void deleteCrimeById() {
        Crime delete = new Crime(24L, "Murder");
        doNothing().when(repository).deleteById(24L);
        deleteCrimeService.deleteCrimeById(24L);

        verify(repository).deleteById(24L);
    }
}