package ml.raketeufo.thi.restbride.entity.grades;

import java.util.ArrayList;
import java.util.List;

public class ExamGroup extends Exam {
    public Boolean group = true;
    public List<Exam> unterPruefungen = new ArrayList<>();
}
