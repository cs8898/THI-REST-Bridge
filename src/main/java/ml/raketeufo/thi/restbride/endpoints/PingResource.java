package ml.raketeufo.thi.restbride.endpoints;

import ml.raketeufo.thi.restbride.entity.dto.response.BaseResponse;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.security.SecuritySchemes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@SecuritySchemes({
        @SecurityScheme(
                securitySchemeName = "JWT AuthToken",
                apiKeyName = "Authorization",
                type = SecuritySchemeType.HTTP,
                in = SecuritySchemeIn.HEADER,
                scheme = "bearer",
                bearerFormat = "JWT"
        )
})


@Path("/ping")
public class PingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Simple Ping to Application", description = "Sends Ping to Application, eg for Monitoring")
    @APIResponse(
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = BaseResponse.class)
            )
    )
    public Response ping() {
        BaseResponse response = new BaseResponse();
        response.ok = true;
        return Response.ok(response).build();
    }
}