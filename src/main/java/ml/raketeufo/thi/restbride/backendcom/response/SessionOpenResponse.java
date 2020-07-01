package ml.raketeufo.thi.restbride.backendcom.response;

import javax.json.JsonArray;
import javax.json.JsonObject;

public class SessionOpenResponse extends BaseResponse {
    private static final int INDEX_SESSION = 0;
    private static final int INDEX_USERNAME = 1;
    private static final int INDEX_MAGICNUMBER = 2;

    private String username;
    private String session;
    private int magicnumber;

    public SessionOpenResponse(JsonObject obj) {
        super(obj);
    }

    public String getUsername() {
        return username;
    }

    public String getSession() {
        return session;
    }

    public int getMagicnumber() {
        return magicnumber;
    }

    @Override
    public void map(JsonObject obj) {
        super.map(obj);
        if (isOk()) {
            JsonArray dataArray = obj.getJsonArray(DATA);
            session = dataArray.getString(INDEX_SESSION);
            username = dataArray.getString(INDEX_USERNAME);
            magicnumber = dataArray.getInt(INDEX_MAGICNUMBER);
        }
    }
}
