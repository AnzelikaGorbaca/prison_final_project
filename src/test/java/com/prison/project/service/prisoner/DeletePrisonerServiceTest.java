package com.prison.project.service.prisoner;

import com.prison.project.repository.PrisonerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePrisonerServiceTest {

    @InjectMocks
    private DeletePrisonerService deletePrisonerService;

    @Mock
    private PrisonerRepository repository;



    @Test
    public void deletePrisoner() {
        Long id = 1L;
        when (repository.deletePrisonerCrimes(id)).thenReturn(0);
        doNothing().when(repository).deleteById(id);

        deletePrisonerService.deletePrisoner(id);
        verify(repository).deleteById(1L);
        verify(repository).deletePrisonerCrimes(1L);

    }
}