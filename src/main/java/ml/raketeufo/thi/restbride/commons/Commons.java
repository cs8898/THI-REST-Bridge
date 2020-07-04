package ml.raketeufo.thi.restbride.commons;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Commons {
    public static final DateTimeFormatter DATE_PARSER = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.GERMANY);
    public static final DateTimeFormatter GERMAN_DATE_PARSER = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY);
    public static final DateTimeFormatter TIME_PARSER = DateTimeFormatter.ofPattern("HH:mm", Locale.GERMANY);
    public static final DateTimeFormatter LONG_TIME_PARSER = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.GERMANY);
    public static final DateTimeFormatter DATE_TIME_PARSER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.GERMANY);

    public static Double parseDoubleString(String str) {
        return Double.parseDouble("0" + str.replace(".", "").replace(",", "."));
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DATE_PARSER);
    }

    public static LocalTime parseTime(String time) {
        return LocalTime.parse(time, TIME_PARSER);
    }

    public static LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, DATE_TIME_PARSER);
    }

    public static LocalTime parseLongTime(String time) {
        return LocalTime.parse(time, LONG_TIME_PARSER);
    }

    public static LocalDate parseGermanDate(String germanDate) {
        String[] splittedDate = germanDate.split(" ");
        splittedDate[0] = splittedDate[splittedDate.length - 1];
        return LocalDate.parse(splittedDate[0], GERMAN_DATE_PARSER);
    }
}
