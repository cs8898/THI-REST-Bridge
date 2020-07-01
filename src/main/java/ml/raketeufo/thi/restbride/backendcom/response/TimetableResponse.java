package ml.raketeufo.thi.restbride.backendcom.response;

import ml.raketeufo.thi.restbride.entity.backend.timetable.Timetable;
import ml.raketeufo.thi.restbride.inputconverter.TimetableConverter;

import javax.json.JsonObject;

public class TimetableResponse extends BaseResponse {
    private Timetable timetable;

    public TimetableResponse(JsonObject obj) {
        super(obj);
    }

    public Timetable getTimetable() {
        return timetable;
    }

    @Override
    public void map(JsonObject obj) {
        super.map(obj);
        if (isOk()) {
            this.timetable = TimetableConverter.convert(obj);
        }
    }
}
