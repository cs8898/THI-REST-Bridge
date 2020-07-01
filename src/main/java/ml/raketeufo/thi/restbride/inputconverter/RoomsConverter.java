package ml.raketeufo.thi.restbride.inputconverter;

import ml.raketeufo.thi.restbride.commons.Commons;
import ml.raketeufo.thi.restbride.entity.backend.room.DateFreeRooms;
import ml.raketeufo.thi.restbride.entity.backend.room.Room;
import ml.raketeufo.thi.restbride.entity.backend.room.TimeSlot;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RoomsConverter {
    public static List<DateFreeRooms> convert(JsonObject json) {
        HashMap<LocalDate, List<Room>> roomsGrouped = new HashMap<>();
        JsonArray dataArray = json.getJsonArray("data");
        JsonObject data = dataArray.getJsonObject(1);
        JsonArray roomsArray = data.getJsonArray("rooms");

        for (JsonValue obj : roomsArray) {
            JsonObject roomsAtDate = (JsonObject) obj;
            LocalDate date = Commons.parseDate(roomsAtDate.getString("datum"));
            JsonArray roomTypes = roomsAtDate.getJsonArray("rtypes");
            HashMap<String, Room> roomHashMap = new HashMap<String, Room>();
            for (JsonValue rObj : roomTypes) {
                JsonObject roomType = (JsonObject) rObj;
                String typeString = roomType.getString("raumtyp");
                JsonObject stunden = roomType.getJsonObject("stunden");
                for (String stunde : stunden.keySet()) {
                    int stundeIndex = Integer.parseInt(stunde) - 1;
                    JsonObject stundeObject = stunden.getJsonObject(stunde);
                    String[] raumList = stundeObject.getString("raeume").split(", ");
                    for (String raum : raumList) {
                        Room room = roomHashMap.getOrDefault(raum, new Room(typeString, raum));
                        TimeSlot currentSlot = new TimeSlot(date, stundeIndex);
                        boolean merged = false;
                        for (TimeSlot oldSlot : room.freeSlots) {
                            if (oldSlot.merge(currentSlot)) {
                                merged = true;
                                break;
                            }
                        }
                        if (!merged) {
                            room.freeSlots.add(currentSlot);
                        }
                        roomHashMap.putIfAbsent(raum, room);
                    }
                }
            }

            List<Room> dateRoomList = roomsGrouped.getOrDefault(date, new ArrayList<>());
            dateRoomList.addAll(roomHashMap.values());
            roomsGrouped.putIfAbsent(date, dateRoomList);
        }
        return roomsGrouped.entrySet().stream()
                .map(e -> new DateFreeRooms(e.getKey(),e.getValue()))
                .collect(Collectors.toList());
    }
}
