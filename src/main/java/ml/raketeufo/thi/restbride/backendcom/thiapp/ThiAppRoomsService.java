package ml.raketeufo.thi.restbride.backendcom.thiapp;

import ml.raketeufo.thi.restbride.backendcom.BaseService;
import ml.raketeufo.thi.restbride.backendcom.response.RoomsResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;

@ApplicationScoped
public class ThiAppRoomsService extends BaseService {
    private static final String SERVICE_NAME = "thiapp";
    private static final String METHOD_ROOMS = "rooms";

    private static final String PARAM_YEAR = "year";
    private static final String PARAM_MONTH = "month";
    private static final String PARAM_DAY = "day";

    protected ThiAppRoomsService() {
        super(SERVICE_NAME);
    }

    public RoomsResponse getRooms(String session) {
        LocalDate now = LocalDate.now(ZoneId.of("Europe/Berlin"));
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        HashMap<String, Object> params = new HashMap<>();
        params.put(PARAM_YEAR, year);
        params.put(PARAM_MONTH, month);
        params.put(PARAM_DAY, day);
        // TODO Use Parameters
        JsonObject response = this.submitRequest(METHOD_ROOMS, session, params);
        return new RoomsResponse(response);
    }
}
