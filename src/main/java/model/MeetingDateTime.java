package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MeetingDateTime {
    private LocalDate meetingDate;
    private TimeSlot timeSlot;

    public LocalDateTime getMeetingDateTime() {
        return LocalDateTime.of(meetingDate, timeSlot.getTimeSlotStart());
    }
}


