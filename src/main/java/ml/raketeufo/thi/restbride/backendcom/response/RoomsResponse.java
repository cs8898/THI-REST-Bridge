package ml.raketeufo.thi.restbride.backendcom.response;

import ml.raketeufo.thi.restbride.entity.backend.room.DateFreeRooms;
import ml.raketeufo.thi.restbride.entity.backend.room.Room;
import ml.raketeufo.thi.restbride.inputconverter.RoomsConverter;

import javax.json.JsonObject;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class RoomsResponse extends BaseResponse {
    List<DateFreeRooms> dates;

    public RoomsResponse(JsonObject obj) {
        super(obj);
    }

    public List<DateFreeRooms> getDates() {
        return dates;
    }

    @Override
    public void map(JsonObject obj) {
        super.map(obj);
        if (isOk()) {
            HashMap<LocalDate, List<Room>> internalRoomsByDate;
            this.dates = RoomsConverter.convert(obj);
        }
    }
}
