package core;

import model.Meeting;
import model.MeetingDateTime;
import model.Person;
import model.TimeSlot;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public interface UsingMeetingManager {


    Map<MeetingDateTime, Meeting> findUpcomingMeetingsByPerson(Person person);

    Set<TimeSlot> suggestTimeSlots(Person person, LocalDate upcomingDate);
}
