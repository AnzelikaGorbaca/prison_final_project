package com.prison.project.service.punishment;
import com.prison.project.model.Punishment;
import com.prison.project.repository.PunishmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class GetPunishmentService {

    private final PunishmentRepository punishmentRepository;

    public List<Punishment> getAllPunishments(){
        return punishmentRepository.findAll();
    }

    public Punishment getPunishmentById(Long id){
        return punishmentRepository.getById(id);
    }

//    public List<Punishment> getAllPunishmentsOrdered(){
//        return punishmentRepository.findAllByImprisonmentMonthsDesc();
//    }
}
