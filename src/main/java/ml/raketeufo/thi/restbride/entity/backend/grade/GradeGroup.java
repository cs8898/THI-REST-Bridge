package ml.raketeufo.thi.restbride.entity.backend.grade;

import java.util.ArrayList;
import java.util.List;

public class GradeGroup extends Grade {
    public GradeGroup() {
        group = true;
        unterPruefungen = new ArrayList<>();
    }
}
