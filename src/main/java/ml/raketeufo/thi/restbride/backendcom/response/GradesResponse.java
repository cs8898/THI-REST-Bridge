package ml.raketeufo.thi.restbride.backendcom.response;

import ml.raketeufo.thi.restbride.entity.backend.grade.Grade;
import ml.raketeufo.thi.restbride.inputconverter.GradeConverter;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbTransient;
import java.util.List;

public class GradesResponse extends BaseResponse {
    private static final int INDEX_MAGICNUMBER = 0;

    @JsonbTransient
    int magicnumber;

    List<Grade> grades;

    public int getMagicnumber() {
        return this.magicnumber;
    }

    public List<Grade> getGrades() {
        return grades;
    }

    public GradesResponse(JsonObject obj) {
        super(obj);
    }

    @Override
    public void map(JsonObject obj) {
        super.map(obj);
        if (isOk()) {
            JsonArray dataArray = obj.getJsonArray(DATA);
            magicnumber = dataArray.getInt(INDEX_MAGICNUMBER);
            this.grades = GradeConverter.convert(obj);
        }
    }
}
