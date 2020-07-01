package ml.raketeufo.thi.restbride.backendcom.response;

import ml.raketeufo.thi.restbride.entity.backend.timetable.Timetable;
import ml.raketeufo.thi.restbride.inputconverter.TimetableConverter;

import javax.json.JsonObject;

public class TimetableResponse extends BaseResponse {
    private Timetable timetable;
    private final boolean details;

    public TimetableResponse(JsonObject obj, boolean useDetails) {
        super(obj);
        this.details = useDetails;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public boolean isDetails() {
        return details;
    }

    @Override
    public void map(JsonObject obj) {
        if (isOk()) {
            this.timetable = TimetableConverter.convert(obj, details);
        }
    }
}
