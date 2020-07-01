package ml.raketeufo.thi.restbride.backendcom.thiapp;

import ml.raketeufo.thi.restbride.backendcom.BaseService;
import ml.raketeufo.thi.restbride.backendcom.response.GradesResponse;
import ml.raketeufo.thi.restbride.backendcom.response.PersDataResponse;
import ml.raketeufo.thi.restbride.backendcom.response.TimetableResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;

@ApplicationScoped
public class ThiAppUserService extends BaseService {
    private static final String SERVICE_NAME = "thiapp";

    private static final String METHOD_PERSDATA = "persdata";
    private static final String METHOD_GRADES = "grades";
    private static final String METHOD_STPL = "stpl";

    private static final String PARAM_YEAR = "year";
    private static final String PARAM_MONTH = "month";
    private static final String PARAM_DAY = "day";
    private static final String PARAM_DETAIL = "details";

    public ThiAppUserService() {
        super(SERVICE_NAME);
    }

    public PersDataResponse getPersonalData(String session) {
        JsonObject response = this.submitRequest(METHOD_PERSDATA, session, null);
        return new PersDataResponse(response);
    }

    public GradesResponse getGrades(String session) {
        JsonObject response = this.submitRequest(METHOD_GRADES, session, null);
        System.out.println(response);
        return new GradesResponse(response);
    }

    public TimetableResponse getTimetable(String session, int detail) {
        LocalDate now = LocalDate.now(ZoneId.of("Europe/Berlin"));
        int year = now.getYear();
        int month = now.getMonthValue();
        int day = now.getDayOfMonth();
        HashMap<String, Object> params = new HashMap<>();
        params.put(PARAM_YEAR, year);
        params.put(PARAM_MONTH, month);
        params.put(PARAM_DAY, day);
        params.put(PARAM_DETAIL, detail);

        JsonObject response = this.submitRequest(METHOD_STPL, session, params);
        return new TimetableResponse(response);
    }
}
