package ml.raketeufo.thi.restbride.entity.backend.room;

import ml.raketeufo.thi.restbride.commons.Commons;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeSlot {
    private static final String[] START_TIMES = {"08:15", "09:00", "09:55", "10:40", "11:35", "12:20", "13:15", "14:00", "14:55", "15:40", "16:35", "17:20", "18:15", "19:00", "19:55", "20:40"};
    private static final String[] END_TIMES = {"09:00", "09:45", "10:40", "11:25", "12:20", "13:05", "14:00", "14:45", "15:40", "16:25", "17:20", "18:05", "19:00", "19:45", "20:40", "21:25"};
    public LocalDateTime start;
    public LocalDateTime end;
    public Integer startIndex;
    public Integer endIndex;

    public TimeSlot(LocalDate date, int index) {
        LocalTime startTime = Commons.parseTime(END_TIMES[index]);
        LocalTime endTime = Commons.parseTime(END_TIMES[index]);
        this.startIndex = index;
        this.endIndex = index;
        this.start = date.atTime(startTime);
        this.end = date.atTime(endTime);
    }

    public boolean merge(TimeSlot otherSlot) {
        if (this.endIndex + 1 == otherSlot.startIndex) {
            //Mergeable after
            this.endIndex = otherSlot.endIndex;
            LocalTime endTime = Commons.parseTime(END_TIMES[endIndex]);
            this.end = end.with(endTime);
            return true;
        } else if (this.startIndex - 1 == otherSlot.endIndex) {
            //Mergable infront
            this.startIndex = otherSlot.startIndex;
            LocalTime startTime = Commons.parseTime(START_TIMES[startIndex]);
            this.start = start.with(startTime);
            return true;
        }
        return false;
    }
}
