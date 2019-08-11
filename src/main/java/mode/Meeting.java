package mode;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;

@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Meeting {

  private Set<Person> attendances = new HashSet<>();

  private LocalDateTime startTime;


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

  public LocalDateTime getEndTime() {
    return getStartTime().plusHours(1L);
  }

  public void setStartTime(LocalDateTime startTime) {
    this.startTime =
        Optional.ofNullable(startTime)
            .orElseThrow(() -> new IllegalArgumentException("Start time is null"));

    if (startTime.isBefore(LocalDateTime.now()))
      throw new IllegalArgumentException("Meeting cannot be booked for the past.");

    this.startTime =
        LocalDateTime.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth(),startTime.getHour(), 0, 0);
  }
}
