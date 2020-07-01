package ml.raketeufo.thi.restbride;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/auth")
public class AuthResource {

    @POST
    public Response createToken(){
        return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }

    @POST
    @Path("refresh")
    public Response refreshToken(){
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).build();
    }
}
