package core;

import model.Meeting;
import model.Person;
import model.TimeSlot;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
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
    person1 = new Person("Mike", "mike@google.com");
    person2 = new Person("Mia", "mia@google.com");
    person3 = new Person("Alex", "Alex@google.com");

    date = LocalDate.of(2019, Month.NOVEMBER, 12);
  }

  @Test
  public void givenPerson_findUpComingMeetings() {
    UsingMeetingManager meetingManager = new MeetingManager();

    Meeting meeting1 = new Meeting();

    meeting1.setStartTime(date, TimeSlot.CLOCK_14);
    meeting1.addAttendance(person1);
    meeting1.addAttendance(person2);

    meetingManager.addMeeting(meeting1);

    Meeting meeting2 = new Meeting();

    meeting2.setStartTime(date, TimeSlot.CLOCK_15);
    meeting2.addAttendance(person1);
    meeting2.addAttendance(person3);

    meetingManager.addMeeting(meeting2);
    meetingManager.findMeetingsByPerson(person1).size();

    assertEquals(2, meetingManager.findMeetingsByPerson(person1).size());
  }

  @Test
  public void GivePersonAndDate_SuggestAvailableSlotsInDay() {
    UsingMeetingManager meetingManager = new MeetingManager();

    Meeting meeting1 = new Meeting();

    meeting1.setStartTime(date, TimeSlot.CLOCK_14);
    meeting1.addAttendance(person1);
    meeting1.addAttendance(person2);

    meetingManager.addMeeting(meeting1);

    Meeting meeting2 = new Meeting();

    meeting2.setStartTime(date, TimeSlot.CLOCK_15);
    meeting2.addAttendance(person1);
    meeting2.addAttendance(person3);

    meetingManager.addMeeting(meeting2);

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

    meeting1.setStartTime(date, TimeSlot.CLOCK_14);
    meeting1.addAttendance(person1);
    meeting1.addAttendance(person2);

    meetingManager.addMeeting(meeting1);

    Meeting meeting2 = new Meeting();

    meeting2.setStartTime(date, TimeSlot.CLOCK_15);
    meeting2.addAttendance(person1);
    meeting2.addAttendance(person3);

    meetingManager.addMeeting(meeting2);

    Set<TimeSlot> slotsOccupied = meetingManager.findPersonOccupiedTimeSlotsInDay(date, person1);

    assertEquals(2, slotsOccupied.size());
  }
}
