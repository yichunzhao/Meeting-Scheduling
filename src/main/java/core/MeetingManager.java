package core;

import model.Meeting;
import model.Person;
import model.TimeSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public class MeetingManager implements UsingMeetingManager {

  private Set<Meeting> allMeetings = new HashSet<>();

  @Override
  public void addMeeting(Meeting meeting) {
    allMeetings.add(meeting);
  }

  /**
   * find upcoming meetings for a person.
   *
   * @param person
   * @return List<Meeting></Meeting>
   */
  @Override
  public Set<Meeting> findMeetingsByPerson(Person person) {
    Optional.ofNullable(person).orElseThrow(() -> new IllegalArgumentException("Person is null "));

    return allMeetings.stream()
        .filter(
            meeting ->
                meeting.getAttendances().contains(person)
                    && meeting.getStartTime().isAfter(LocalDateTime.now()))
        .collect(toSet());
  }

  /**
   * For a specific date(current or upcoming) Given a persons, and a date, suggesting available
   * slots for this person.
   */
  @Override
  public Set<TimeSlot> suggestTimeSlots(Person person, LocalDate upcomingDate) {

    if (upcomingDate.isBefore(LocalDate.now()))
      throw new IllegalArgumentException("please input current date or future");

    // find out occupied time slots for the date
    Set<TimeSlot> occupiedSlots = findPersonOccupiedTimeSlotsInDay(upcomingDate, person);

    // suggest not-occupied slots.
    return findNotOccupiedTimeSlotsInDay(occupiedSlots);
  }

  /**
   * for this person on this date, find out his time slots used.
   * */
  public Set<TimeSlot> findPersonOccupiedTimeSlotsInDay(LocalDate date, Person person) {
    // on this date, person has following slots occupied.
    return allMeetings.stream()
        .filter(
            m ->
                m.getAttendances().contains(person) && m.getStartTime().toLocalDate().isEqual(date))
        .map(m -> m.getTimeSlot())
        .collect(toSet());
  }

  /** Given occupied slots, find out un-occupied slots in a day. */
  public Set<TimeSlot> findNotOccupiedTimeSlotsInDay(Set<TimeSlot> occupied) {
    return Arrays.stream(TimeSlot.values())
        .filter(slot -> !occupied.contains(slot))
        .collect(toSet());
  }
}
