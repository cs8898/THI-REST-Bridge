package ml.raketeufo.thi.restbride.backendcom.response;

import javax.json.JsonObject;
import javax.json.bind.annotation.JsonbTransient;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class BaseResponse {

    public enum Status {
        OK(0),
        SERVICE_NOT_AVAILABLE(-112),
        NO_SESSION(-115),
        NO_RESERVATION_DATA(-126),
        UNKNOWN(Integer.MIN_VALUE);

        private final int code;

        Status(int code) {
            this.code = code;
        }

        public static Status fromCode(int anInt) {
            for (Status s : Status.values()) {
                if (s.getCode() == anInt) {
                    return s;
                }
            }
            return UNKNOWN;
        }

        public int getCode() {
            return this.code;
        }
    }

    protected static final String STATUS = "status";
    protected static final String DATE = "date";
    protected static final String TIME = "time";
    protected static final String DATA = "data";

    @JsonbTransient
    protected Status status;
    protected String error;

    protected LocalDateTime timestamp;

    public BaseResponse(JsonObject obj) {
        this.map(obj);
    }

    public Status getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getError() {
        return error;
    }

    public boolean isOk() {
        return status == Status.OK;
    }

    public void map(JsonObject obj) {
        status = Status.fromCode(obj.getInt(STATUS));
        timestamp = LocalDateTime.now(ZoneId.of("Europe/Berlin"));
        //TODO USE Provided Date and Time

        if (!isOk()) {
            this.error = obj.getString(DATA);
        }
    }
}

