package core;

import mode.Meeting;
import mode.Person;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;

public class MeetingManagerTest {
  private Person person1;
  private Person person2;
  private Person person3;

  @Before
  public void setup() {
    person1 = new Person("Mike", "mike@google.com");
    person2 = new Person("Mia", "mia@google.com");
    person3 = new Person("Alex", "Alex@google.com");
  }

  @Test
  public void findMeetingsByPerson() {
    MeetingManager meetingManager = new MeetingManager();

    Meeting meeting1 = new Meeting();
    LocalDateTime time1 = LocalDateTime.of(2019, Month.DECEMBER, 28, 9, 0, 0);
    meeting1.setStartTime(time1);
    meeting1.addAttendance(person1);
    meeting1.addAttendance(person2);

    meetingManager.addMeeting(meeting1);

    Meeting meeting2 = new Meeting();
    LocalDateTime time2 = LocalDateTime.of(2019, Month.NOVEMBER, 28, 10, 0, 0);
    meeting2.setStartTime(time2);
    meeting2.addAttendance(person1);
    meeting2.addAttendance(person3);

    meetingManager.addMeeting(meeting2);
    meetingManager.findMeetingsByPerson(person1).size();

    assertEquals(2, meetingManager.findMeetingsByPerson(person1).size());
  }
}
