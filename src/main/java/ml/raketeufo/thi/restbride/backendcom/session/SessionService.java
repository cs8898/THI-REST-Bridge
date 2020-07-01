package ml.raketeufo.thi.restbride.backendcom.session;

import ml.raketeufo.thi.restbride.backendcom.BaseService;
import ml.raketeufo.thi.restbride.backendcom.response.BaseResponse;
import ml.raketeufo.thi.restbride.backendcom.response.SessionOpenResponse;

import javax.enterprise.context.ApplicationScoped;
import javax.json.JsonObject;
import java.util.HashMap;

/**
 * THI Session Service
 */
@ApplicationScoped
public class SessionService extends BaseService {
    public static final String SERVICE_NAME = "session";

    public static final String METHOD_OPEN = "open";
    private static final String METHOD_ISALIVE = "isalive";
    private static final String METHOD_CLOSE = "close";

    public static final String PARAM_USERNAME = "username";
    public static final String PARAM_PASSWD = "passwd";

    public SessionService() {
        super(SERVICE_NAME);
    }

    public SessionOpenResponse login(String username, String password) {
        HashMap<String, Object> params = new HashMap<>();
        params.put(PARAM_USERNAME, username);
        params.put(PARAM_PASSWD, password);

        JsonObject response = this.submitRequest(METHOD_OPEN, params);

        return new SessionOpenResponse(response);
    }

    public BaseResponse test(String session) {
        JsonObject response = this.submitRequest(METHOD_ISALIVE, session, null);
        return new BaseResponse(response);
    }

    public BaseResponse close(String session) {
        JsonObject response = this.submitRequest(METHOD_CLOSE, session, null);
        return new BaseResponse(response);
    }
}
