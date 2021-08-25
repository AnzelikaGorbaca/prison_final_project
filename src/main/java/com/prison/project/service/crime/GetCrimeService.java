package com.prison.project.service.crime;

import com.prison.project.model.Crime;
import com.prison.project.repository.CrimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class GetCrimeService {
    private final CrimeRepository crimeRepository;

    public List<Crime> getAllCrime() {
        return crimeRepository.findAll();
    }

    public Crime getCrimeById(Long id) {
        return crimeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Crime not found"));
    }

    public List<Crime> getCrimeByIds(List<Long> ids) {
        return crimeRepository.findAllById(ids);
    }

    public Crime getCrimeByDescription(String crimeDescription) {
        return crimeRepository.findByCrimeDescription(crimeDescription)
                .orElseThrow(() -> new RuntimeException("Crime not found"));
    }

    public List<Crime> getCrimesJson(String crimeJson) {
        List<Long> crimeIds = Arrays.stream(crimeJson.split(","))
                .map(Long::valueOf)
                .collect(Collectors.toList());

        return getCrimeByIds(crimeIds);
    }

    public List<Long> getCrimePrisoner(Long id) {
        return crimeRepository.getPrisonerCrimes(id);
    }

}
