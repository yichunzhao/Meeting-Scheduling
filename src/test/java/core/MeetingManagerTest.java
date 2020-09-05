package core;

import model.Email;
import model.Meeting;
import model.MeetingDateTime;
import model.Name;
import model.Person;
import model.TimeSlot;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

public class MeetingManagerTest {
    private Person person1;
    private Person person2;
    private Person person3;

    private Meeting meeting1;
    private Meeting meeting2;
    private Meeting meeting3;

    private LocalDate date;

    @BeforeEach
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

    @AfterEach
    void done() {
        System.out.println("++++++++++++++++++++++++++ unit test done ++++++++++++++++++++++++++++++++++");
        person1 = null;
        person2 = null;
        person3 = null;
        meeting1 = null;
        meeting2 = null;
        meeting3 = null;
        date = null;
    }

    @BeforeAll
    static void initForAllTests() {
        System.out.println("......................... Init all tests ....................................");
    }

    @AfterAll
    static void allTestDone() {
        System.out.println("........................ all unit test done .................................");
    }

    @Test
    void assumptionsAboutMeetings() {
        //two upcoming meetings and one meeting in the past.
        assumeTrue(meeting1.getMeetingDateTime().getMeetingDate().isAfter(LocalDate.now()));
        assumeTrue(meeting2.getMeetingDateTime().getMeetingDate().isAfter(LocalDate.now()));
        assumeTrue(meeting3.getMeetingDateTime().getMeetingDate().isBefore(LocalDate.now()));
    }

    @Test
    void givenPerson_findUpComingMeetings() {

        MeetingManager meetingManager = MeetingManager.instance();

        meetingManager.createMeetingPerson(meeting1, person1);

        meetingManager.createMeetingPersons(meeting2, person1, person3);
        meetingManager.createMeetingPersons(meeting3, person1, person3);

        meetingManager.findUpcomingMeetingsByPerson(person1).size();

        assertEquals(2, meetingManager.findUpcomingMeetingsByPerson(person1).size());
    }

    @Test
    void givenPersons_findUpComingMeetings() {
        MeetingManager meetingManager = MeetingManager.instance();
    }

    @Test
    void GivePersonAndDate_SuggestAvailableSlotsInDay() {
        MeetingManager meetingManager = MeetingManager.instance();

        meetingManager.createMeetingPersons(meeting1, Stream.of(person1, person2).collect(toSet()));

        meetingManager.createMeetingPersons(meeting2, Stream.of(person1, person3).collect(toSet()));

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
    void givenListSlotsUsed_FindUnUsedSlotsInDay() {
        MeetingManager meetingManager = MeetingManager.instance();

        Set<TimeSlot> occupied = Stream.of(TimeSlot.CLOCK_10, TimeSlot.CLOCK_16).collect(toSet());
        Set<TimeSlot> actual = meetingManager.findNotOccupiedTimeSlotsInDay(occupied);

        Set<TimeSlot> expected =
                Arrays.stream(TimeSlot.values()).filter(slot -> !occupied.contains(slot)).collect(toSet());

        assertEquals(expected, actual);
    }

    @Test
    void givenPersonAndDate_FindOutOccupiedSlotsInDay() {
        MeetingManager meetingManager = MeetingManager.instance();

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

    @Test
    @DisplayName("when find a non-existed person, it throws an NoSuchElementException.")
    void findPersonWhoIsNotExisted_throwException() {
        Person person = new Person(Name.of("ynz"), Email.of("ynz@mail.com"));

        Throwable exception = assertThrows(NoSuchElementException.class,
                () -> MeetingManager.instance().findPersonMeetings(person));

        assertEquals(exception.getMessage(), "Person is not found: " + person.getName());
    }

    @Test
    @DisplayName("when creating a person having the existing email")
    void findCreatePersonWithExistingEmail_throwException() {
        MeetingManager meetingManager = MeetingManager.instance();

        meetingManager.createMeetingPerson(meeting1, person1);

        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> meetingManager.createMeetingPerson(meeting1, new Person(Name.of("ynz"), person1.getEmail())));

        assertTrue(exception.getMessage().contains("The email address is already existed:"));
    }

    @Test
    void assignedTimeSlotConflicting_throwException() {
        MeetingManager meetingManager = MeetingManager.instance();
        meetingManager.createMeetingPersons(meeting1, person1, person2, person3);

        Meeting meeting4 = new Meeting("another meeting", new MeetingDateTime(date.plusDays(1L), TimeSlot.CLOCK_14));
        Throwable throwable = assertThrows(IllegalStateException.class, () -> meetingManager.createMeetingPerson(meeting4, person1));

        assertTrue(throwable.getMessage().contains("meeting slot is occupied"));
    }

    @Test
    @Disabled
    void sameDateAndTimeSlot_givesSameHashCode() {
        MeetingDateTime meetingDateTime = new MeetingDateTime(date.plusDays(1L), TimeSlot.CLOCK_14);
        MeetingDateTime meetingDateTime_ = new MeetingDateTime(date.plusDays(1L), TimeSlot.CLOCK_14);
        assertEquals(meetingDateTime.hashCode(), meetingDateTime_.hashCode());
    }

    @Test
    @Disabled
    void sameDateDifferentTimeSlot_givesDifferentHashCode() {
        MeetingDateTime meetingDateTime = new MeetingDateTime(date.plusDays(1L), TimeSlot.CLOCK_15);
        MeetingDateTime meetingDateTime_ = new MeetingDateTime(date.plusDays(1L), TimeSlot.CLOCK_14);
        assertNotEquals(meetingDateTime.hashCode(), meetingDateTime_.hashCode());
    }

    @Test
    void testSuggestPersonTimeSlots() {
        MeetingManager meetingManager = MeetingManager.instance();
        meetingManager.createMeetingPersons(meeting1, person1, person2);
        meetingManager.createMeetingPersons(meeting2, person1, person3);
        meetingManager.createMeetingPersons(meeting3, person1, person2, person3);

        Set<Person> persons = Stream.of(person1, person2, person3).collect(toSet());
        LocalDate date = meeting2.getMeetingDateTime().getMeetingDate();

        Map<Person, Set<TimeSlot>> personsTimeSlots = meetingManager.suggestPersonsTimeSlots(persons, date);

        assertAll("check all persons",
                () -> assertEquals(TimeSlot.values().length - 1, personsTimeSlots.get(person1).size()),
                () -> assertEquals(TimeSlot.values().length - 1, personsTimeSlots.get(person3).size()),
                () -> assertEquals(TimeSlot.values().length, personsTimeSlots.get(person2).size())
        );
    }


}
