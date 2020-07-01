package ml.raketeufo.thi.restbride.backendcom;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jose4j.json.internal.json_simple.JSONObject;

import javax.json.JsonObject;
import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class BaseService {
    //@ConfigProperty(name = "bridge.backend.baseurl", defaultValue = "https://hiplan.thi.de/webservice/production2/index.php")
    public String BASE_URL = "https://hiplan.thi.de/webservice/production2/index.php";
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newCachedThreadPool();

    private final String service;
    private final WebTarget target;

    protected BaseService(String service) {
        this.service = service;
        this.target = ClientBuilder.newBuilder()
                .executorService(EXECUTOR_SERVICE)
                .build()
                .target(BASE_URL);
    }

    protected JsonObject submitRequest(String method, HashMap<String, Object> params) {
        return submitRequest(method, null, params);
    }

    protected JsonObject submitRequest(String method, String session, HashMap<String, Object> params) {
        RequestBody.Builder bodyBuilder = new RequestBody.Builder()
                .json()
                .service(this.service)
                .method(method);

        if (params != null) {
            bodyBuilder.addObjects(params);
        }

        if (session != null) {
            bodyBuilder.session(session);
        }


        MultivaluedHashMap<String, String> multivaluedParams = bodyBuilder.build().getMultivaluedFields();
        Entity<Form> form = Entity.form(multivaluedParams);
        Invocation.Builder builder = target.request();
        Response response = builder
                .accept(MediaType.APPLICATION_JSON_TYPE)
                .buildPost(form)
                .invoke();

        int status = response.getStatus();

        return response.readEntity(JsonObject.class);
    }
}
