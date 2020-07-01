package ml.raketeufo.thi.restbride.backendcom.response;

import ml.raketeufo.thi.restbride.entity.backend.exam.Exam;
import ml.raketeufo.thi.restbride.entity.backend.user.UserInformation;
import ml.raketeufo.thi.restbride.inputconverter.ExamConverter;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbTransient;
import java.util.List;

public class GradesResponse extends BaseResponse {
    private static final int INDEX_MAGICNUMBER = 0;

    @JsonbTransient
    int magicnumber;

    List<Exam> exams;

    public int getMagicnumber() {
        return this.magicnumber;
    }

    public List<Exam> getExams() {
        return exams;
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
            this.exams = ExamConverter.convert(obj);
        }
    }
}
