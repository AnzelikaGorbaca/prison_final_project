package com.prison.project.service.statistics;

import com.prison.project.model.Prisoner;
import com.prison.project.service.prisoner.GetPrisonerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final GetPrisonerService getPrisonerService;

    public List<Prisoner> getListPrisonerWithCrimeCountForStatistics(){
        List<Prisoner> prisonerList = getPrisonerService.getAllWithStatus();
        List<Prisoner> resultList = getPrisonerListSorted(prisonerList);

        return resultList;
    }

    private List<Prisoner> getPrisonerListSorted(List<Prisoner> prisonerList) {
        Map<Prisoner, Integer> map = new HashMap<>();
        for (Prisoner p: prisonerList){
            map.put(p, p.getCrimes().size());
        }

        LinkedHashMap<Prisoner, Integer> a = map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        List <Prisoner> resultList = new ArrayList(a.keySet());
        return resultList;
    }


}
