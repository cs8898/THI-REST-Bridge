package ml.raketeufo.thi.restbride.entity.backend.timetable;

import ml.raketeufo.thi.restbride.commons.Commons;

import java.time.LocalDate;

public class IntervalEvent extends BaseEvent {
    public IntervalEvent(String von, String bis, String intervall) {
        LocalDate start = Commons.parseDate(von);
        LocalDate end = Commons.parseDate(bis);
        this.start = start.atTime(0, 0, 0);
        this.end = end.atTime(23, 59, 59);
        this.title = intervall;
    }
}
