package core;

import model.Email;
import model.Meeting;
import model.MeetingDateTime;
import model.Person;
import model.TimeSlot;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.junit.Assert.assertEquals;

public class MeetingManagerTest {
    private Person person1;
    private Person person2;
    private Person person3;

    private LocalDate date;

    @Before
    public void setup() {
        person1 = new Person("Mike", new Email("mike@google.com"));
        person2 = new Person("Mia", new Email("mia@google.com"));
        person3 = new Person("Alex", new Email("Alex@google.com"));

        date = LocalDate.now().plusDays(1L);
    }

    @Test
    public void givenPerson_findUpComingMeetings() {
        MeetingManager meetingManager = new MeetingManager();

        Meeting meeting1 = new Meeting("meeting_1", new MeetingDateTime(date, TimeSlot.CLOCK_14));


        meetingManager.createMeetingPerson(meeting1, person1);

        Meeting meeting2 = new Meeting("meeting_2", new MeetingDateTime(date, TimeSlot.CLOCK_15));


        meetingManager.createMeetingPerson(meeting2, person1);
        meetingManager.createMeetingPerson(meeting2, person3);

        meetingManager.findUpcomingMeetingsByPerson(person1).size();

        assertEquals(2, meetingManager.findUpcomingMeetingsByPerson(person1).size());
    }

    @Test
    public void GivePersonAndDate_SuggestAvailableSlotsInDay() {
        MeetingManager meetingManager = new MeetingManager();

        Meeting meeting1 = new Meeting();

        meeting1.setMeetingDateTime(new MeetingDateTime(date, TimeSlot.CLOCK_14));

        meetingManager.createMeetingPerson(meeting1, person1);
        meetingManager.createMeetingPerson(meeting1, person2);

        Meeting meeting2 = new Meeting();

        meeting2.setMeetingDateTime(new MeetingDateTime(date, TimeSlot.CLOCK_15));

        meetingManager.createMeetingPerson(meeting2, person1);
        meetingManager.createMeetingPerson(meeting2, person3);


        long actual = meetingManager.suggestTimeSlots(person1, date).size();

        long expected =
                Arrays.stream(TimeSlot.values())
                        .filter(slot -> !Arrays.asList(TimeSlot.CLOCK_14, TimeSlot.CLOCK_15).contains(slot))
                        .count();

        assertEquals(expected, actual);
    }

    @Test
    public void givenListSlotsUsed_FindUnUsedSlotsInDay() {
        MeetingManager meetingManager = new MeetingManager();

        Set<TimeSlot> occupied = Stream.of(TimeSlot.CLOCK_10, TimeSlot.CLOCK_16).collect(toSet());
        Set<TimeSlot> actual = meetingManager.findNotOccupiedTimeSlotsInDay(occupied);

        Set<TimeSlot> expected =
                Arrays.stream(TimeSlot.values()).filter(slot -> !occupied.contains(slot)).collect(toSet());

        assertEquals(expected, actual);
    }

    @Test
    public void givenPersonAndDate_FindOutOccupiedSlotsInDay() {
        MeetingManager meetingManager = new MeetingManager();

        Meeting meeting1 = new Meeting();

        meeting1.setMeetingDateTime(new MeetingDateTime(date, TimeSlot.CLOCK_14));

        meetingManager.createMeetingPerson(meeting1, person1);
        meetingManager.createMeetingPerson(meeting1, person2);

        Meeting meeting2 = new Meeting();

        meeting2.setMeetingDateTime(new MeetingDateTime(date, TimeSlot.CLOCK_15));

        meetingManager.createMeetingPerson(meeting2, person1);
        meetingManager.createMeetingPerson(meeting2, person3);

        Set<TimeSlot> slotsOccupied = meetingManager.findPersonOccupiedTimeSlotsInDay(date, person1);

        assertEquals(2, slotsOccupied.size());
    }
}
