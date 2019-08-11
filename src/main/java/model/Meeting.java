package model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Meeting {

  private Set<Person> attendances = new HashSet<>();

  private LocalDateTime startTime;

  private TimeSlot timeSlot;

  public void addAttendance(Person person) {
    attendances.add(
        Optional.ofNullable(person)
            .orElseThrow(() -> new IllegalArgumentException("Person is null")));
  }

  public Set<Person> getAttendances() {
    return Collections.unmodifiableSet(attendances);
  }

  public LocalDateTime getStartTime() {
    return Optional.ofNullable(startTime)
        .orElseThrow(() -> new IllegalStateException("Meeting time is not setup yet"));
  }

  public TimeSlot getTimeSlot() {
    return Optional.ofNullable(timeSlot)
        .orElseThrow(() -> new IllegalStateException("TimeSlot is not setup yet"));
  }

  public LocalDateTime getEndTime() {
    return getStartTime().plusHours(1L);
  }

  public void setStartTime(LocalDate date, TimeSlot slot) {

    Optional.ofNullable(date).orElseThrow(() -> new IllegalArgumentException("Start time is null"));

    startTime =
        LocalDateTime.of(
            date.getYear(), date.getMonth(), date.getDayOfMonth(), slot.getHourMark(), 0, 0);

    if (startTime.isBefore(LocalDateTime.now()))
      throw new IllegalArgumentException("Meeting cannot be booked for the past.");

    this.timeSlot = slot;
  }
}
