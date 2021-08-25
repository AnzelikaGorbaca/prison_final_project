package com.prison.project.service.punishment;

import com.prison.project.repository.PunishmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class DeletePunishmentService {

    private final PunishmentRepository punishmentRepository;

    public void deletePunishment(Long id) {

        List<Long> punishmentList = punishmentRepository.getPrisonerPunishment(id);
        if (punishmentList.isEmpty()) {
            punishmentRepository.deleteById(id);
        }
    }

    public List<Long> getPunishmentPrisoner(Long id) {
        return punishmentRepository.getPrisonerPunishment(id);
    }

}
