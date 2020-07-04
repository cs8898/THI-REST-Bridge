package ml.raketeufo.thi.restbride.backendcom.response;

import ml.raketeufo.thi.restbride.entity.backend.exam.Exam;
import ml.raketeufo.thi.restbride.entity.backend.timetable.Timetable;
import ml.raketeufo.thi.restbride.inputconverter.ExamConverter;
import ml.raketeufo.thi.restbride.inputconverter.TimetableConverter;

import javax.json.JsonObject;
import java.util.List;

public class ExamsResponse extends BaseResponse {
    private List<Exam> exams;

    public ExamsResponse(JsonObject obj) {
        super(obj);
    }

    public List<Exam> getExams() {
        return exams;
    }

    @Override
    public void map(JsonObject obj) {
        super.map(obj);
        if (isOk()) {
            this.exams = ExamConverter.convert(obj);
        }
    }
}
