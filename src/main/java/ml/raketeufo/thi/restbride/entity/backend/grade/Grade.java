package ml.raketeufo.thi.restbride.entity.backend.grade;

import java.util.ArrayList;
import java.util.List;

public class Grade {
    public String titel;
    public Double note;
    public Double ects;
    public Boolean angerechnet;
    public String fristSemester;
    public Boolean wahlPflichtFach;
    public String kztn;
    public String modulNummer;
    public String studiengang;
    public Boolean group;
    public List<Grade> unterPruefungen;

    public int fehlversuche;
}
