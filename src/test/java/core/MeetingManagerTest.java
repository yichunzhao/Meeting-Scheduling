package core;

import model.Email;
import model.Meeting;
import model.MeetingDateTime;
import model.Name;
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

    private Meeting meeting1;
    private Meeting meeting2;
    private Meeting meeting3;

    private LocalDate date;

    @Before
    public void setup() {
        date = LocalDate.now();

        person1 = new Person(Name.of("Mike"), Email.of("mike@google.com"));
        person2 = new Person(Name.of("Mia"), Email.of("mia@google.com"));
        person3 = new Person(Name.of("Alex"), Email.of("Alex@google.com"));

        //meeting in one day
        meeting1 = new Meeting("meeting_1", new MeetingDateTime(date.plusDays(1L), TimeSlot.CLOCK_14));

        //meeting in two days
        meeting2 = new Meeting("meeting_2", new MeetingDateTime(date.plusDays(2L), TimeSlot.CLOCK_15));

        //meeting in one day ago
        meeting3 = new Meeting("meeting_3", new MeetingDateTime(date.minusDays(1L), TimeSlot.CLOCK_15));
    }

    @Test
    public void givenPerson_findUpComingMeetings() {
        MeetingManager meetingManager = new MeetingManager();

        meetingManager.createMeetingPerson(meeting1, person1);

        meetingManager.createMeetingPerson(meeting2, person1);
        meetingManager.createMeetingPerson(meeting2, person3);

        meetingManager.createMeetingPerson(meeting3, person1);
        meetingManager.createMeetingPerson(meeting3, person3);

        meetingManager.findUpcomingMeetingsByPerson(person1).size();

        assertEquals(2, meetingManager.findUpcomingMeetingsByPerson(person1).size());
    }

    @Test
    public void GivePersonAndDate_SuggestAvailableSlotsInDay() {
        MeetingManager meetingManager = new MeetingManager();

        meetingManager.createMeetingPerson(meeting1, person1);
        meetingManager.createMeetingPerson(meeting1, person2);

        meetingManager.createMeetingPerson(meeting2, person1);
        meetingManager.createMeetingPerson(meeting2, person3);

        int numOfSlots = TimeSlot.values().length;

        assertEquals(numOfSlots, meetingManager.suggestTimeSlots(person1, date).size());
        assertEquals(numOfSlots - 1, meetingManager.suggestTimeSlots(person1, date.plusDays(1L)).size());
        assertEquals(numOfSlots - 1, meetingManager.suggestTimeSlots(person1, date.plusDays(2L)).size());

        assertEquals(numOfSlots, meetingManager.suggestTimeSlots(person2, date).size());
        assertEquals(numOfSlots - 1, meetingManager.suggestTimeSlots(person1, date.plusDays(1L)).size());
        assertEquals(numOfSlots, meetingManager.suggestTimeSlots(person2, date.plusDays(2L)).size());

        assertEquals(numOfSlots, meetingManager.suggestTimeSlots(person3, date).size());
        assertEquals(numOfSlots, meetingManager.suggestTimeSlots(person3, date.plusDays(1L)).size());
        assertEquals(numOfSlots - 1, meetingManager.suggestTimeSlots(person3, date.plusDays(2L)).size());
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

        meetingManager.createMeetingPerson(meeting1, person1);
        meetingManager.createMeetingPerson(meeting1, person2);

        meetingManager.createMeetingPerson(meeting2, person1);
        meetingManager.createMeetingPerson(meeting2, person3);

        assertEquals(1, meetingManager.findPersonOccupiedTimeSlotsInDay(date.plusDays(1L), person1).size());
        assertEquals(1, meetingManager.findPersonOccupiedTimeSlotsInDay(date.plusDays(1L), person2).size());
        assertEquals(1, meetingManager.findPersonOccupiedTimeSlotsInDay(date.plusDays(2L), person1).size());
        assertEquals(1, meetingManager.findPersonOccupiedTimeSlotsInDay(date.plusDays(2L), person3).size());

        assertEquals(0, meetingManager.findPersonOccupiedTimeSlotsInDay(date.plusDays(2L), person2).size());
        assertEquals(0, meetingManager.findPersonOccupiedTimeSlotsInDay(date.plusDays(1L), person3).size());
    }
}
