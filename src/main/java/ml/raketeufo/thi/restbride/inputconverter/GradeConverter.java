package ml.raketeufo.thi.restbride.inputconverter;

import ml.raketeufo.thi.restbride.commons.Commons;
import ml.raketeufo.thi.restbride.entity.backend.grade.Grade;
import ml.raketeufo.thi.restbride.entity.backend.grade.GradeGroup;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GradeConverter {
    public static List<Grade> convert(JsonObject json) {
        List<Grade> grades = new ArrayList<>();
        JsonArray dataArray = json.getJsonArray("data");
        JsonArray data = dataArray.getJsonArray(1);
        for (JsonValue val : data) {
            JsonObject obj = val.asJsonObject();
            String kztn = obj.getString("kztn");
            Grade grade;
            if (kztn.startsWith("M")) {
                grade = new GradeGroup();
            } else {
                grade = new Grade();
            }

            grade.fehlversuche = 0;
            grade.angerechnet = "*".equals(obj.getString("anrech"));
            grade.ects = Commons.parseDoubleString(obj.getString("ects"));
            grade.fristSemester = obj.getString("fristsem");
            grade.kztn = kztn;
            grade.modulNummer = obj.getString("pon");
            try {
                String noteString = obj.getString("note").replace("*", "");
                if ("E".equals(noteString)) {
                    noteString = "1,0";
                }
                if (noteString.contains("-")) {
                    noteString = noteString.replace("-", "");
                    grade.fehlversuche++;
                }
                grade.note = Commons.parseDoubleString(noteString);
            } catch (NumberFormatException e) {
                System.out.println("Error Parsing:\n" + obj.toString());
                throw e;
            }
            grade.studiengang = obj.getString("stg");
            grade.titel = obj.getString("titel");
            //exam.wahlPflichtFach frwpf
            if (!(grade instanceof GradeGroup) && !grade.kztn.endsWith("_")) {
                //ADD To ExamGroup based on PoN (modulNummer)
                Optional<Grade> examGroup = grades.stream().filter(ex -> ex instanceof GradeGroup && ex.modulNummer.equals(grade.modulNummer))
                        .findAny();
                examGroup.ifPresent(value -> ((GradeGroup) value).unterPruefungen.add(grade));
            } else {
                grades.add(grade);
            }
        }
        return grades;
    }
}
