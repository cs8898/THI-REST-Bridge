package ml.raketeufo.thi.restbride.entity.backend.timetable;

import ml.raketeufo.thi.restbride.commons.Commons;

public class CourseEvent extends BaseEvent {
    public String fach;
    public String studiengang;
    public String studiengruppe;
    public int teilgruppe;
    public String dozent;
    public String room;
    public int sws;
    public int ects;

    // DETAILS
    public String pruefung;
    public String ziel;
    public String inhalt;
    public String literatur;

    //pruefung, ziel, inhalt, literatur

    public CourseEvent(String datum, String von, String bis, String veranstaltung) {
        this.start = Commons.parseDateTime(datum + " " + von);
        this.end = Commons.parseDateTime(datum + " " + bis);
        this.title = veranstaltung;
    }
}
