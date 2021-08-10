package com.prison.project.service.prisoner;

import com.prison.project.model.Prisoner;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
@Transactional
@Service
@AllArgsConstructor
public class StatusPrisonerService {


    public void checkIfInPrison(Prisoner prisoner) {
        LocalDate endDate = prisoner.getEndDate();
        if (endDate.compareTo(LocalDate.now()) <= 0) {
            prisoner.setInPrison(false);
            prisoner.setStatus("Freed");
        }
    }

}
