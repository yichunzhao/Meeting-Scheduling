package mode;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MeetingTest {

  private Person person1;
  private Person person2;
  private Person person3;

  private LocalDateTime startTime;

  @Before
  public void setUp() throws Exception {
    person1 = new Person("Mike", "mike@google.com");
    person2 = new Person("Mia", "mia@google.com");
    person3 = new Person("Alex", "Alex@google.com");

    startTime = LocalDateTime.of(2019, Month.AUGUST, 12, 12, 0, 0);
  }

  @Test
  public void whenAddThreePersonsIntoMeeting_GetAttendanceSizeThree() {
    Meeting meeting = new Meeting();

    meeting.setStartTime(startTime);
    meeting.addAttendance(person1);
    meeting.addAttendance(person2);
    meeting.addAttendance(person3);

    assertEquals(meeting.getAttendances().size(), 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenAddNullPersonsIntoMeeting_ThenThrowException() {
    Meeting meeting = new Meeting();

    meeting.addAttendance(null);
  }

  @Test
  public void whenMeetingIsJustCreated_GetAttendanceSizeZero() {
    Meeting standUp = new Meeting();
    assertEquals(standUp.getAttendances().size(), 0);
  }

  @Test(expected = IllegalStateException.class)
  public void whenMeetingIsJustCreated_GetStartTime_ThrowException() {
    Meeting meeting = new Meeting();
    meeting.getStartTime();
  }

  @Test(expected = IllegalStateException.class)
  public void getEndTime() {
    Meeting meeting = new Meeting();
    meeting.getEndTime();
  }

  @Test
  public void whenGetStartTime_ItEscapeReference() {
    Meeting meeting = new Meeting();
    meeting.setStartTime(startTime);

    assertTrue(startTime != meeting.getStartTime());
  }

  @Test
  public void whenGetStartTime_ItEqualsTimeSet() {
    Meeting meeting = new Meeting();
    meeting.setStartTime(startTime);

    assertEquals(startTime, meeting.getStartTime());
  }

  @Test
  public void whenGetEndTime_ItIsLateThanStartTime() {
    Meeting meeting = new Meeting();
    meeting.addAttendance(person1);
    meeting.addAttendance(person3);

    meeting.setStartTime(startTime);
    assertTrue(meeting.getEndTime().isAfter(startTime));
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetTimeBeforeNow_ItThrowException() {
    Meeting meeting = new Meeting();
    meeting.addAttendance(person1);
    meeting.addAttendance(person3);

    LocalDateTime past = LocalDateTime.of(2018, Month.FEBRUARY, 10, 10, 12, 15);
    meeting.setStartTime(past);
  }
}
