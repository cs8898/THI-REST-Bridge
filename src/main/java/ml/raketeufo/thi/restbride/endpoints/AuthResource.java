package ml.raketeufo.thi.restbride.endpoints;

import ml.raketeufo.thi.restbride.config.ConfigProvider;
import ml.raketeufo.thi.restbride.entity.dto.response.AuthResponse;
import ml.raketeufo.thi.restbride.entity.dto.response.BaseResponse;
import ml.raketeufo.thi.restbride.jwt.JWTClaims;
import ml.raketeufo.thi.restbride.backendcom.session.SessionService;
import ml.raketeufo.thi.restbride.backendcom.response.SessionOpenResponse;
import ml.raketeufo.thi.restbride.entity.dto.request.LoginBody;
import ml.raketeufo.thi.restbride.jwt.TokenGenerator;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonString;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/auth")
@RequestScoped
public class AuthResource {

    @Inject
    SessionService sessionService;

    private static final String JWT_CLAIM_SID = "sid";

    @Inject
    @Claim(JWT_CLAIM_SID)
    Optional<JsonString> jwtSession;

    @Inject
    TokenGenerator tokenGenerator;

    @Inject
    ConfigProvider configProvider;

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Starten einer neuen Session", description = "Erstellt eine Neue Session im Backend, und bettet diese in einen JWT Token")
    @APIResponses({
            @APIResponse(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = AuthResponse.class)
                    )
            ),
            @APIResponse(
                    responseCode = "401",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON,
                            schema = @Schema(implementation = BaseResponse.class)
                    )
            )})
    public Response createToken(@RequestBody(required = true) LoginBody loginBody) {
        SessionOpenResponse response = sessionService.login(loginBody.username, loginBody.passwd);
        if (response.isOk()) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.ok = true;
            authResponse.token = tokenGenerator.generateFrom(response);
            authResponse.exp = configProvider.getAuthExpiryTime();

            return Response.ok(authResponse)
                    .header(HttpHeaders.CACHE_CONTROL, "no-store")
                    .header("Pragma", "no-cache")
                    .build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
    }

    @GET
    @SecurityRequirement(name = "JWT AuthToken")
    @RolesAllowed("user")
    @Path("test")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = BaseResponse.class)
            )
    )
    @Operation(summary = "Testet SessionID am Backend", description = "Testet am Backend ob die Session noch Gültig ist")
    public Response testSession() {
        if (jwtSession.isPresent()) {
            String session = jwtSession.get().getString();
            return Response.ok(sessionService.test(session)).build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @POST
    @SecurityRequirement(name = "JWT AuthToken")
    @RolesAllowed("user")
    @Path("close")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = BaseResponse.class)
            )
    )
    @Operation(summary = "Beendet die Session im Backend", description = "Beendet die Session im Backend")
    public Response stopSession() {
        if (jwtSession.isPresent()) {
            String session = jwtSession.get().getString();
            return Response.ok(sessionService.close(session)).build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}