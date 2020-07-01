package ml.raketeufo.thi.restbride.entity.backend.timetable;

import java.util.ArrayList;
import java.util.List;

public class Timetable {
    public String semester;
    public List<SpecialEvent> specials = new ArrayList<>();
    public List<CourseEvent> courses = new ArrayList<>();
    public List<IntervalEvent> intervals = new ArrayList<>();
}
