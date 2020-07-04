package ml.raketeufo.thi.restbride.entity.backend.exam;

import java.time.LocalDateTime;

public class Exam {
    public String titel;
    public String anmeldeCode;
    public LocalDateTime anmeldeZeit;
    public String anmerkung;
    public String[] rooms;
    public String seat;
    public LocalDateTime zeit;
    public String[] hilfsmittel;
    public Pruefer[] pruefer;
    public boolean ausserhalbZeitraum;
    public String katalogId;
    public String art;
    public String studiengang;
    public String semester;
}
