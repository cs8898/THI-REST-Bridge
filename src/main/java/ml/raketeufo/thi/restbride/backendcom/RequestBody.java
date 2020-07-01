package ml.raketeufo.thi.restbride.backendcom;

import javax.ws.rs.core.MultivaluedHashMap;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private HashMap<String, String> fields;

    private RequestBody() {
        this.fields = new HashMap<>();
    }

    public static class Builder {
        private static final String FIELD_SERVICE = "service";
        private static final String FIELD_METHOD = "method";
        private static final String FIELD_FORMAT = "format";
        private static final String FIELD_SESSION = "session";

        private final RequestBody body;

        public Builder() {
            this.body = new RequestBody();
        }

        public RequestBody.Builder service(String service) {
            body.fields.put(FIELD_SERVICE, service);
            return this;
        }

        public RequestBody.Builder method(String method) {
            body.fields.put(FIELD_METHOD, method);
            return this;
        }

        public RequestBody.Builder json() {
            body.fields.put(FIELD_FORMAT, "json");
            return this;
        }

        public RequestBody.Builder session(String session) {
            body.fields.put(FIELD_SESSION, session);
            return this;
        }

        public Builder add(String key, String value) {
            body.fields.put(key, value);
            return this;
        }

        public Builder addObjects(HashMap<String, Object> params) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                this.add(entry.getKey(), entry.getValue().toString());
            }
            return this;
        }

        public Builder add(HashMap<String, String> params) {
            body.fields.putAll(params);
            return this;
        }

        public RequestBody build() {
            return body;
        }
    }

    public HashMap<String, String> getFields() {
        return this.fields;
    }

    public MultivaluedHashMap<String, String> getMultivaluedFields() {
        return new MultivaluedHashMap<>(this.fields);
    }
}
