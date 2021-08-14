package com.prison.project.service.prisoner;

import com.prison.project.model.Prisoner;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
public class StatusPrisonerService {


    public void checkIfInPrisonAndSetStatus(List<Prisoner> prisoners) {
        for (Prisoner p: prisoners) {
            LocalDate endDate = p.getEndDate();
            if (endDate.compareTo(LocalDate.now()) <= 0) {
                p.setInPrison(false);
                p.setStatus("Freed");
            } else {
                p.setInPrison(true);
                p.setStatus("In Prison");
            }
        }
    }

    public void checkIfInPrisonAndSetStatus(Prisoner p) {
            LocalDate endDate = p.getEndDate();
            if (endDate.compareTo(LocalDate.now()) <= 0) {
                p.setInPrison(false);
                p.setStatus("Freed");
            } else {
                p.setInPrison(true);
                p.setStatus("In Prison");
            }
        }
    }

