package com.prison.project.service.crime;

import com.prison.project.model.Crime;
import com.prison.project.repository.CrimeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@AllArgsConstructor
public class CreateCrimeService {

    public final CrimeRepository crimeRepository;

    public Crime registerCrime (Crime crime){
        crimeRepository.save(crime);
        return crime;
    }
}
