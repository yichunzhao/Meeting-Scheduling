package model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.Assert.assertTrue;

public class MeetingTest {

    private Person person1;
    private Person person2;
    private Person person3;

    private LocalDate date;
    private LocalTime time;

    @Before
    public void setUp() throws Exception {
        person1 = new Person("Mike", Email.of("mike@google.com"));
        person2 = new Person("Mia", Email.of("mia@google.com"));
        person3 = new Person("Alex", Email.of("Alex@google.com"));

        date = LocalDate.of(2019, Month.DECEMBER, 12);
        time = TimeSlot.CLOCK_14.getTimeSlotStart();
    }

}
