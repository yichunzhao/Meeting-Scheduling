package core;

import mode.Meeting;
import mode.Person;
import mode.TimeSlot;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

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
  public void findMeetingsByPerson() {
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
    meetingManager.findMeetingsByPerson(person1).size();

    assertEquals(2, meetingManager.findMeetingsByPerson(person1).size());
  }
}
