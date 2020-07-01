package ml.raketeufo.thi.restbride.inputconverter;

import ml.raketeufo.thi.restbride.commons.Commons;
import ml.raketeufo.thi.restbride.entity.backend.exam.Exam;
import ml.raketeufo.thi.restbride.entity.backend.exam.ExamGroup;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExamConverter {
    public static List<Exam> convert(JsonObject json) {
        List<Exam> exams = new ArrayList<>();
        JsonArray dataArray = json.getJsonArray("data");
        JsonArray data = dataArray.getJsonArray(1);
        for (JsonValue val : data) {
            JsonObject obj = val.asJsonObject();
            String kztn = obj.getString("kztn");
            Exam exam;
            if (kztn.startsWith("M")) {
                exam = new ExamGroup();
            } else {
                exam = new Exam();
            }

            exam.fehlversuche = 0;
            exam.angerechnet = "*".equals(obj.getString("anrech"));
            exam.ects = Commons.parseDoubleString(obj.getString("ects"));
            exam.fristSemester = obj.getString("fristsem");
            exam.kztn = kztn;
            exam.modulNummer = obj.getString("pon");
            try {
                String noteString = obj.getString("note").replace("*", "");
                if ("E".equals(noteString)) {
                    noteString = "1,0";
                }
                if (noteString.contains("-")) {
                    noteString = noteString.replace("-", "");
                    exam.fehlversuche++;
                }
                exam.note = Commons.parseDoubleString(noteString);
            } catch (NumberFormatException e) {
                System.out.println("Error Parsing:\n" + obj.toString());
                throw e;
            }
            exam.studiengang = obj.getString("stg");
            exam.titel = obj.getString("titel");
            //exam.wahlPflichtFach frwpf
            if (!(exam instanceof ExamGroup) && !exam.kztn.endsWith("_")) {
                //ADD To ExamGroup based on PoN (modulNummer)
                Optional<Exam> examGroup = exams.stream().filter(ex -> ex instanceof ExamGroup && ex.modulNummer.equals(exam.modulNummer))
                        .findAny();
                examGroup.ifPresent(value -> ((ExamGroup) value).unterPruefungen.add(exam));
            } else {
                exams.add(exam);
            }
        }
        return exams;
    }
}
