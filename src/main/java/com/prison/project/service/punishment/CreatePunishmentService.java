package com.prison.project.service.punishment;

import com.prison.project.model.Punishment;
import com.prison.project.repository.PunishmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CreatePunishmentService {

    private final PunishmentRepository punishmentRepository;

    public Punishment registerPunishment (Punishment punishment){
        punishmentRepository.save(punishment);
        return punishment;
    }

}
