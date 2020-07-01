package ml.raketeufo.thi.restbride.entity.backend.room;

import java.time.LocalDate;
import java.util.List;

public class DateFreeRooms {
    private final LocalDate date;
    private final List<Room> rooms;

    public DateFreeRooms(LocalDate date, List<Room> rooms) {
        this.date = date;
        this.rooms = rooms;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<Room> getRooms() {
        return rooms;
    }
}
