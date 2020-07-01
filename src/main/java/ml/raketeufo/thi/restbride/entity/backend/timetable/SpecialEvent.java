package ml.raketeufo.thi.restbride.entity.backend.timetable;

import ml.raketeufo.thi.restbride.commons.Commons;

import java.time.LocalDate;

public class SpecialEvent extends BaseEvent {
    public SpecialEvent(String date, String name) {
        LocalDate start = Commons.parseDate(date);
        this.start = start.atTime(0, 0, 0);
        this.end = start.atTime(23, 59, 59);
        this.title = name;
    }
}
