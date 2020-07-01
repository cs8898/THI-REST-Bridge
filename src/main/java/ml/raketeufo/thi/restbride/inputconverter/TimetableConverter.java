package ml.raketeufo.thi.restbride.inputconverter;

import ml.raketeufo.thi.restbride.entity.backend.timetable.CourseEvent;
import ml.raketeufo.thi.restbride.entity.backend.timetable.IntervalEvent;
import ml.raketeufo.thi.restbride.entity.backend.timetable.SpecialEvent;
import ml.raketeufo.thi.restbride.entity.backend.timetable.Timetable;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class TimetableConverter {
    public static Timetable convert(JsonObject json, boolean useDetails) {
        Timetable timetable = new Timetable();
        JsonArray dataArray = json.getJsonArray("data");

        timetable.semester = dataArray.getString(1);

        JsonArray specialEvents = dataArray.getJsonArray(2);
        for (JsonValue val : specialEvents) {
            JsonObject obj = val.asJsonObject();
            String datum = obj.getString("datum");
            String name = obj.getString("name");
            SpecialEvent specialEvent = new SpecialEvent(datum, name);

            timetable.specials.add(specialEvent);
        }

        JsonArray courseEvents = dataArray.getJsonArray(3);
        for (JsonValue val : courseEvents) {
            JsonObject obj = val.asJsonObject();
            String datum = obj.getString("datum");
            String von = obj.getString("von");
            String bis = obj.getString("bis");
            String veranstaltung = obj.getString("veranstaltung");
            CourseEvent courseEvent = new CourseEvent(datum, von, bis, veranstaltung);

            courseEvent.fach = obj.getString("fach");
            courseEvent.studiengang = obj.getString("stg");
            courseEvent.studiengruppe = obj.getString("stgru");
            courseEvent.teilgruppe = Integer.parseInt(obj.getString("teilgruppe"));
            courseEvent.dozent = obj.getString("dozent");
            courseEvent.room = obj.getString("raum");
            courseEvent.sws = Integer.parseInt(obj.getString("sws"));
            courseEvent.ects = Integer.parseInt(obj.getString("ectspoints"));

            if (useDetails) {
                courseEvent.pruefung = obj.getString("pruefung");
                courseEvent.ziel = obj.getString("ziel");
                courseEvent.inhalt = obj.getString("inhalt");
                courseEvent.literatur = obj.getString("literatur");
            }

            timetable.courses.add(courseEvent);
        }

        JsonArray IntervalEvents = dataArray.getJsonArray(4);
        for (JsonValue val : IntervalEvents) {
            JsonObject obj = val.asJsonObject();
            String von = obj.getString("von");
            String bis = obj.getString("bis");
            String intervall = obj.getString("intervall");
            IntervalEvent intervalEvent = new IntervalEvent(von, bis, intervall);

            timetable.intervals.add(intervalEvent);
        }

        return timetable;
    }
}
