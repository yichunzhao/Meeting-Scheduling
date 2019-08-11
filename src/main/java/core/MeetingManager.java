package core;

import mode.Meeting;
import mode.Person;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class MeetingManager {

  private Set<Meeting> allMeetings = new HashSet<>();

  public void addMeeting(Meeting meeting) {
    allMeetings.add(meeting);
  }

  /**
   * find upcoming meetings for a person.
   *
   * @param person
   * @return List<Meeting></Meeting>
   */
  public List<Meeting> findMeetingsByPerson(Person person) {
    Optional.ofNullable(person).orElseThrow(() -> new IllegalArgumentException("Person is null "));

    return allMeetings.stream()
        .filter(
            meeting ->
                meeting.getAttendances().contains(person)
                    && meeting.getStartTime().isAfter(LocalDateTime.now()))
        .collect(toList());
  }

  /**
   * Given a list of persons, and suggest
   *
   * @param persons
   * @return
   */
  public List<LocalDateTime> suggestTimeSlots(Set<Person> persons) {

    List<LocalDateTime> slots = null;

    return slots;
  }
}
