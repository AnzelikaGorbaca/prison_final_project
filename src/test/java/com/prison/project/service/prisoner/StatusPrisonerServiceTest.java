package com.prison.project.service.prisoner;

import com.prison.project.model.Crime;
import com.prison.project.model.Prisoner;
import com.prison.project.model.Punishment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StatusPrisonerServiceTest {

    @InjectMocks
    StatusPrisonerService statusPrisonerService;

    private final Punishment punishment = new Punishment(1L, 5);

    private final List<Crime> crimes1 = Arrays.asList(new Crime(2L, "Murder"),
            new Crime(3L, "Robbery"),
            new Crime(4L, "Awful cook"));

    private final List<Crime> crimes2 = Arrays.asList(new Crime(2L, "Murder"),
            new Crime(3L, "Robbery"));

    private LocalDate getStartDate() {
        String start = "2021-08-13";
        return LocalDate.parse(start);
    }

    private LocalDate getEndDate(String end) {
        return LocalDate.parse(end);
    }

    LocalDate endDateEquals = getStartDate();
    LocalDate endDateBefore = getEndDate("2021-08-14");
    LocalDate endDateAfter = getEndDate("2021-12-14");

    List<Prisoner> prisoners = Arrays.asList(new Prisoner(2L, "Janis", "Ozolins", "310856 - 10605",
                    "Rigas iela 4-5", getStartDate(), null, "Janis.jpg",
                    true, "In Prison", crimes1, punishment, punishment.getId(), "Murder, Robbery, Awful Cook"),
            (new Prisoner(3L, "Peteris", "Zarins", "190665 - 10005",
                    "Rigas iela 4-5", getStartDate(), null, "Peteris.jpg",
                    true, "In Prison", crimes2, punishment, punishment.getId(), "Murder, Robbery")));

    Prisoner prisoner = prisoners.get(1);

    @Test
    void checkIfInPrisonAndSetStatusWhenItIsEqualDate() {
        prisoners.get(0).setEndDate(endDateEquals);
        prisoners.get(1).setEndDate(endDateEquals);

        statusPrisonerService.checkIfInPrisonAndSetStatus(prisoners);
        assertEquals("Freed", prisoners.get(0).getStatus());
        assertEquals("Freed", prisoners.get(1).getStatus());
        assertFalse(prisoners.get(0).getInPrison());
        assertFalse(prisoners.get(1).getInPrison());

    }


    @Test
    void checkIfInPrisonAndSetStatusWhenItIsBeforeDate() {

        prisoners.get(0).setEndDate(endDateBefore);
        prisoners.get(1).setEndDate(endDateBefore);

        statusPrisonerService.checkIfInPrisonAndSetStatus(prisoners);
        assertEquals("Freed", prisoners.get(0).getStatus());
        assertEquals("Freed", prisoners.get(1).getStatus());
        assertFalse(prisoners.get(0).getInPrison());
        assertFalse(prisoners.get(1).getInPrison());
    }

    @Test
    void checkIfInPrisonAndSetStatusWhenItIsAfterDate() {
        prisoners.get(0).setEndDate(endDateAfter);
        prisoners.get(1).setEndDate(endDateAfter);

        statusPrisonerService.checkIfInPrisonAndSetStatus(prisoners);
        assertEquals("In Prison", prisoners.get(0).getStatus());
        assertEquals("In Prison", prisoners.get(1).getStatus());
        assertTrue(prisoners.get(0).getInPrison());
        assertTrue(prisoners.get(1).getInPrison());
    }


    @Test
    void testCheckIfInPrisonAndSetStatusWhenItIsEqualDate() {
        prisoner.setEndDate(endDateEquals);
        statusPrisonerService.checkIfInPrisonAndSetStatus(prisoner);
        assertEquals("Freed", prisoner.getStatus());
        assertFalse(prisoner.getInPrison());
    }

    @Test
    void testCheckIfInPrisonAndSetStatusWhenItIsBeforeDate() {
        prisoner.setEndDate(endDateBefore);
        statusPrisonerService.checkIfInPrisonAndSetStatus(prisoner);
        assertEquals("Freed", prisoner.getStatus());
        assertFalse(prisoner.getInPrison());
    }

    @Test
    void testCheckIfInPrisonAndSetStatusWhenItIsAfterDate() {
        prisoner.setEndDate(endDateAfter);
        statusPrisonerService.checkIfInPrisonAndSetStatus(prisoner);
        assertEquals("In Prison", prisoner.getStatus());
        assertTrue(prisoner.getInPrison());
    }
}
