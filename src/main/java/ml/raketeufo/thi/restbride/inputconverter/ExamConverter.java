package ml.raketeufo.thi.restbride.inputconverter;

import ml.raketeufo.thi.restbride.commons.Commons;
import ml.raketeufo.thi.restbride.commons.PgArrayUtils;
import ml.raketeufo.thi.restbride.entity.backend.exam.Exam;
import ml.raketeufo.thi.restbride.entity.backend.exam.Pruefer;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ExamConverter {

    public static List<Exam> convert(JsonObject json) {
        List<Exam> exams = new ArrayList<>();
        JsonArray dataArray = json.getJsonArray("data");
        JsonArray data = dataArray.getJsonArray(1);
        for (JsonValue val : data) {
            JsonObject obj = val.asJsonObject();
            String titel = obj.getString("titel");

            String anmeldeCode = obj.getString("ancode");
            LocalDate anmeldeDate = Commons.parseDate(obj.getString("anm_date"));
            LocalDateTime anmeldeZeit = anmeldeDate.atTime(Commons.parseLongTime(obj.getString("anm_time")));
            String anmerkung = obj.getString("anmerkung", "");
            String plainRooms = obj.getString("exam_rooms", "");
            String[] rooms = {};
            if (plainRooms != null && !plainRooms.isEmpty())
                rooms = plainRooms.split(" & ");
            String seat = obj.getString("exam_seat", "");
            String plainExamTime = obj.getString("exam_time", "");
            LocalTime examTime;
            LocalDateTime examDate = null;
            if (!plainExamTime.isEmpty()) {
                examTime = Commons.parseTime(plainExamTime);
                String plainExamDate = obj.getString("exm_date", "");
                if (plainExamDate != null) {
                    examDate = Commons.parseGermanDate(plainExamDate).atTime(examTime);
                }
            }

            String plainPruefer = obj.getString("pruefer_namen");
            String[] prueferSplit = plainPruefer.replace(",  ", ";").split(", ");
            ArrayList<Pruefer> prueferList = new ArrayList<>();
            for (String prueferComb : prueferSplit) {
                String[] prueferSplitted = prueferComb.split(";");
                prueferList.add(new Pruefer(prueferSplitted[0], prueferSplitted[1]));
            }
            String hilfsmitte_pgstr = obj.getString("hilfsmittel");
            List<String> hilfsmitte = PgArrayUtils.parseStringArray(hilfsmitte_pgstr);
            String art = obj.getString("pruefungs_art");
            int mode = Integer.parseInt(obj.getString("modus"));
            String katalogId = obj.getString("prf_katalog_id");

            String semester = obj.getString("sem");
            String studiengang = obj.getString("stg");

            Exam exam = new Exam();
            exam.anmeldeCode = anmeldeCode;
            exam.anmeldeZeit = anmeldeZeit;
            exam.anmerkung = anmerkung;
            exam.art = art;
            exam.ausserhalbZeitraum = mode == 2;
            exam.hilfsmittel = hilfsmitte.toArray(new String[0]);
            exam.katalogId = katalogId;
            exam.pruefer = prueferList.toArray(new Pruefer[0]);
            exam.rooms = rooms;
            exam.seat = seat;
            exam.zeit = examDate;
            exam.semester = semester;
            exam.studiengang = studiengang;
            exam.titel = titel;

            exams.add(exam);
        }
        return exams;
    }

}
