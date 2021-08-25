package com.prison.project.service.crime;

import com.prison.project.repository.CrimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class DeleteCrimeService {

    private final CrimeRepository crimeRepository;

    public void deleteCrimeById(Long id) {
         crimeRepository.deleteById(id);
    }


}
