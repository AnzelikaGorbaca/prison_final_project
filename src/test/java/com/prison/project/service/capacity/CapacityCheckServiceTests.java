package com.prison.project.service.capacity;

import com.prison.project.model.PrisonCapacity;
import com.prison.project.repository.PrisonerRepository;
import com.prison.project.service.prisonCapacity.PrisonCapacityCheck;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CapacityCheckServiceTests {
    @Mock
    private PrisonerRepository prisonerRepository;


    @InjectMocks
    private PrisonCapacityCheck prisonCapacityCheck;

    @Mock
    private PrisonCapacity prisonCapacity;


    @Test
    void shouldReturnFreeSpacesOnDistinctDate()  {
        String date = "2022-01-01";
        LocalDate localDate = LocalDate.parse(date);

        when(prisonerRepository.countByEndDateGreaterThan(localDate)).thenReturn(90L);

      //  prisonCapacity.getCapacity().longValue();

        Long freePlaces = prisonCapacityCheck.getFreePlacesByDate(localDate);

        assertEquals(10L, freePlaces);

    }

    /*

    public Long getFreePlacesByDate(LocalDate localDate) {
        Long prisonerCount = prisonerRepository.countByEndDateGreaterThan(localDate);
        long freeSpaces = prisonCapacity.getCapacity() - prisonerCount;
        if (freeSpaces > 0) {
            return freeSpaces;
        }
        return 0L;
    }
     */

}
