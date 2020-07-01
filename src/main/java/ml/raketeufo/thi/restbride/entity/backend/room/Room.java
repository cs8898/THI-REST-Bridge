package ml.raketeufo.thi.restbride.entity.backend.room;

import java.util.ArrayList;
import java.util.List;

public class Room {
    public String type;
    public String room;
    public List<TimeSlot> freeSlots;

    public Room(String type, String room) {
        this.type = type;
        this.room = room;
        this.freeSlots = new ArrayList<>();
    }
}
