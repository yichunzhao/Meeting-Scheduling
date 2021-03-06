package core;

import lombok.RequiredArgsConstructor;
import model.Email;
import model.Meeting;
import model.MeetingDateTime;
import model.Person;
import model.TimeSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@RequiredArgsConstructor(staticName = "instance")
public class MeetingManager {
    //existing emails(contains O(1))
    private Set<Email> emailSet = new HashSet<>();

    //store person, meetingDateTime, and meeting in one map.
    private Map<Person, Map<MeetingDateTime, Meeting>> personDateTimeMeetingMap = new HashMap<>();

    private void createPerson(Person person) {
        if (emailSet.contains(person.getEmail()))
            throw new IllegalArgumentException("The email address is already existed: " + person.getEmail());

        emailSet.add(person.getEmail());
        personDateTimeMeetingMap.put(person, new HashMap<>());
    }

    public Map<MeetingDateTime, Meeting> findPersonMeetings(Person person) {
        Map<MeetingDateTime, Meeting> found = this.personDateTimeMeetingMap.get(person);
        if (found == null) throw new NoSuchElementException("Person is not found: " + person.getName());
        return found;
    }

    /**
     * Involving one person in a meeting.
     */
    public void createMeetingPerson(Meeting meeting, Person person) {
        if (!personDateTimeMeetingMap.keySet().contains(person)) createPerson(person);

        MeetingDateTime dateTime = meeting.getMeetingDateTime();

        if (personDateTimeMeetingMap.get(person).keySet().contains(dateTime))
            throw new IllegalStateException("meeting slot is occupied");

        personDateTimeMeetingMap.get(person).put(dateTime, meeting);
    }

    /**
     * Involving persons in a meeting.
     */
    public void createMeetingPersons(Meeting meeting, Set<Person> persons) {
        persons.forEach(person -> createMeetingPerson(meeting, person));
    }

    public void createMeetingPersons(Meeting meeting, Person... persons) {
        Arrays.stream(persons).forEach(person -> createMeetingPerson(meeting, person));
    }

    /**
     * Find upcoming meetings for a person.
     */
    public Map<MeetingDateTime, Meeting> findUpcomingMeetingsByPerson(Person person) {
        Optional.ofNullable(person).orElseThrow(() -> new IllegalArgumentException("Person is null "));

        return findPersonMeetings(person)
                .entrySet()
                .stream()
                .collect(Collectors.partitioningBy(meetingDateTimeMeetingEntry -> meetingDateTimeMeetingEntry.getKey().getMeetingDateTime().isAfter(LocalDateTime.now())))
                .get(true)
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * For a specific date(current or upcoming) Given a persons, and a date, suggesting available
     * slots for this person.
     */
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
     */
    public Set<TimeSlot> findPersonOccupiedTimeSlotsInDay(LocalDate date, Person person) {
        // on this date, person has following slots occupied.
        return findPersonMeetings(person)
                .entrySet()
                .stream()
                .filter(dateTimeMeetingEntry -> dateTimeMeetingEntry.getKey().getMeetingDate().isEqual(date))
                .map(dateTimeMeetingEntry -> dateTimeMeetingEntry.getKey().getTimeSlot())
                .collect(toSet());
    }

    /**
     * Given occupied slots, find out un-occupied slots in a day.
     */
    public Set<TimeSlot> findNotOccupiedTimeSlotsInDay(Set<TimeSlot> occupied) {
        return Arrays.stream(TimeSlot.values())
                .filter(slot -> !occupied.contains(slot))
                .collect(toSet());
    }

    /**
     * Suggest one or more available timeslots for meetings given a group of persons.
     */
    public Map<Person, Set<TimeSlot>> suggestPersonsTimeSlots(Set<Person> persons, LocalDate date) {
        return persons.stream().collect(Collectors.toMap(person -> person, person -> suggestTimeSlots(person, date)));
    }

}
