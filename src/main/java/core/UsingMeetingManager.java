package core;

import mode.Meeting;
import mode.Person;
import mode.TimeSlot;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface UsingMeetingManager {
    void addMeeting(Meeting meeting);

    List<Meeting> findMeetingsByPerson(Person person);

    Set<TimeSlot> suggestTimeSlots(Person person, LocalDate upcomingDate);
}
