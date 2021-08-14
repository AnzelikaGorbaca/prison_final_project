//package com.prison.project.service.statistics;
//
//import com.prison.project.model.Crime;
//import com.prison.project.model.Prisoner;
//import com.prison.project.repository.PrisonerRepository;
//import com.prison.project.service.prisoner.GetPrisonerService;
//import com.prison.project.service.prisoner.StatusPrisonerService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class StatisticsServiceTest {
//
//    @InjectMocks
//    private StatisticsService statisticsService;
//    @InjectMocks
//    private GetPrisonerService getPrisonerService;
//    @InjectMocks
//    private StatusPrisonerService statusPrisonerService;
//
//    @Mock
//    private PrisonerRepository prisonerRepository;
//
//    @Test
//    void shouldReturnSortedListOfPrisonersByCrimeCount() {
//        final Prisoner prisoner1 = new Prisoner();
//        List<Crime> list1 = new ArrayList<>();
//        list1.add(new Crime(1L, "A"));
//        list1.add(new Crime(2L, "B"));
//        prisoner1.setCrimes(list1);
//
//        final Prisoner prisoner2 = new Prisoner();
//        List<Crime> list2 = new ArrayList<>();
//        list2.add(new Crime(4L, "A"));
//        list2.add(new Crime(5L, "B"));
//        list2.add(new Crime(3L, "C"));
//        prisoner2.setCrimes(list2);
//
//        when(getPrisonerService.getAllWithStatus())
//                .thenReturn(prisonerRepository.findAll()));
//        doNothing().when(statusPrisonerService.checkIfInPrisonAndSetStatus(prisonerRepository.findAll()));
//       when(prisonerRepository.findAll())
//               .thenReturn(Arrays.asList(prisoner2, prisoner1));
//
//       List<Prisoner> actualList = statisticsService.getListPrisonerWithCrimeCountForStatistics();
//
//       assertNotNull(actualList);
//       assertEquals(actualList.get(0).getCrimes().size(), prisoner2.getCrimes().size());
//       assertEquals(actualList.get(1).getCrimes().size(), prisoner1.getCrimes().size());
//
//        verify(prisonerRepository).findAll();
//    }
//}