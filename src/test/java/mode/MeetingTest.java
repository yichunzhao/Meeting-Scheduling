package mode;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MeetingTest {

  private Person person1;
  private Person person2;
  private Person person3;

  private LocalDate date;
  private LocalTime time;

  @Before
  public void setUp() throws Exception {
    person1 = new Person("Mike", "mike@google.com");
    person2 = new Person("Mia", "mia@google.com");
    person3 = new Person("Alex", "Alex@google.com");

    date = LocalDate.of(2019, Month.DECEMBER, 12);
    time = LocalTime.of(TimeSlot.CLOCK_14.getHourMark(), 0, 0);
  }

  @Test
  public void whenAddThreePersonsIntoMeeting_GetAttendanceSizeThree() {
    Meeting meeting = new Meeting();

    meeting.setStartTime(date, TimeSlot.CLOCK_11);
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
  public void whenGetStartTime_ItEqualsTimeSet() {
    Meeting meeting = new Meeting();
    meeting.setStartTime(date, TimeSlot.CLOCK_10);

    LocalTime time = LocalTime.of(TimeSlot.CLOCK_10.getHourMark(), 0, 0);
    assertEquals(LocalDateTime.of(date, time), meeting.getStartTime());
  }

  @Test
  public void whenGetEndTime_ItIsLateThanStartTime() {
    Meeting meeting = new Meeting();
    meeting.addAttendance(person1);
    meeting.addAttendance(person3);

    meeting.setStartTime(date, TimeSlot.CLOCK_11);
    LocalTime time = LocalTime.of(TimeSlot.CLOCK_11.getHourMark(), 0, 0);

    assertTrue(meeting.getEndTime().isAfter(LocalDateTime.of(date, time)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void whenSetTimeBeforeNow_ItThrowException() {
    Meeting meeting = new Meeting();
    meeting.addAttendance(person1);
    meeting.addAttendance(person3);

    LocalDate pastDate = LocalDate.of(2018, Month.FEBRUARY, 10);
    meeting.setStartTime(pastDate, TimeSlot.CLOCK_16);
  }
}
