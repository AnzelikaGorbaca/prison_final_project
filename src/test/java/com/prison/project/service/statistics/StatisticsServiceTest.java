package com.prison.project.service.statistics;

import com.prison.project.model.Crime;
import com.prison.project.model.Prisoner;
import com.prison.project.service.prisoner.GetPrisonerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StatisticsServiceTest {

    private Prisoner prisoner1;
    private Prisoner prisoner2;
    private Prisoner prisoner3;

    @InjectMocks
    private StatisticsService statisticsService;

    @Mock
    private GetPrisonerService getPrisonerService;

    private final List<Crime> list1 = Arrays.asList(new Crime(1L, "A"), new Crime(2L, "B"));
    private final List<Crime> list2 = Arrays.asList(new Crime(4L, "A"), new Crime(5L, "B"), new Crime(3L, "C"));
    private final List<Crime> list3 = Arrays.asList(new Crime(5L, "A"));

    @BeforeEach
    void setUp() {
        prisoner1 = new Prisoner();
        prisoner2 = new Prisoner();
        prisoner3 = new Prisoner();
    }

    @Test
    void shouldReturnSortedListOfPrisonersByCrimeCount() {
        prisoner1.setCrimes(list1);
        prisoner2.setCrimes(list2);
        prisoner3.setCrimes(list3);

        List<Prisoner> prisoners = Arrays.asList(prisoner2, prisoner1, prisoner3);

        when(getPrisonerService.getAllWithStatus())
                .thenReturn(prisoners);
        List<Prisoner> actualList = statisticsService.getListPrisonerWithCrimeCountForStatistics();

        assertNotNull(actualList);
        assertEquals(actualList.get(0).getCrimes().size(), prisoner2.getCrimes().size());
        assertEquals(actualList.get(1).getCrimes().size(), prisoner1.getCrimes().size());

        verify(getPrisonerService).getAllWithStatus();
    }

    @Test
    void shouldSortTheList()throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = StatisticsService.class
                .getDeclaredMethod("getPrisonerListSorted", List.class);
        method.setAccessible(true);

        prisoner1.setCrimes(list1);
        prisoner2.setCrimes(list2);
        prisoner3.setCrimes(list3);

        List<Prisoner> prisoners = Arrays.asList( prisoner1, prisoner2, prisoner3);
        List<Prisoner> expected = Arrays.asList(prisoner2, prisoner1, prisoner3);
        List<Prisoner> actual = (List<Prisoner>) method.invoke(statisticsService, prisoners);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}