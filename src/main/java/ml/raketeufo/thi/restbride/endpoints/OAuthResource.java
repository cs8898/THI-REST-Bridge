package ml.raketeufo.thi.restbride.endpoints;

import ml.raketeufo.thi.restbride.backendcom.response.SessionOpenResponse;
import ml.raketeufo.thi.restbride.backendcom.session.SessionService;
import ml.raketeufo.thi.restbride.config.ConfigProvider;
import ml.raketeufo.thi.restbride.entity.dto.request.OAuthBody;
import ml.raketeufo.thi.restbride.jwt.TokenGenerator;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameters;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.jose4j.base64url.Base64;

import javax.annotation.security.PermitAll;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

@Path("oauth")
@PermitAll
@ApplicationScoped
public class OAuthResource {

    private static final Set<String> supportedGrantTypes = Collections.singleton("password");

    @Inject
    ConfigProvider configProvider;

    @Inject
    SessionService sessionService;

    @Inject
    TokenGenerator tokenGenerator;

    @POST
    @Path("token")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Grant with OAuth", description = "Password Flow im Oauth Stil, äquivalent zu /auth")
    @RequestBody(
            name = "OAuth Form",
            required = true,
            description = "OAuth Formular nach https://www.oauth.com/oauth2-servers/access-tokens/password-grant/",
            content = @Content(
                    mediaType = MediaType.APPLICATION_FORM_URLENCODED,
                    schema = @Schema(
                            type = SchemaType.OBJECT,
                            implementation = OAuthBody.class
                    )
            )
    )
    @Parameter(
            name = HttpHeaders.AUTHORIZATION,
            in = ParameterIn.HEADER,
            description = "OAuth Client Basic Auth",
            example = "Basic [base64 clientId:clientSecret"
    )
    public Response postToken(@RequestBody OAuthBody params,
                              @HeaderParam(HttpHeaders.AUTHORIZATION)
                              @DefaultValue("") String authHeader) {
        if (params.grantType == null) {
            JsonObject error = Json.createObjectBuilder()
                    .add("error", "invalid_request")
                    .add("error_description", "grant_type params is required")
                    .build();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error)
                    .build();
        }
        if (!supportedGrantTypes.contains(params.grantType)) {
            JsonObject error = Json.createObjectBuilder()
                    .add("error", "unsupported_grant_type")
                    .add("error_description", "grant type should be one of :" + supportedGrantTypes)
                    .build();
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(error).build();
        }

        String clientId;
        String clientSecret;
        if (authHeader.length() > 0) {
            String[] clientCredentials = extractFromAuthHeader(authHeader);
            clientId = clientCredentials[0];
            clientSecret = clientCredentials[1];
        } else {
            clientId = params.clientId;
            clientSecret = params.clientSecret;
        }

        boolean allowedClient = checkAllowedClients(clientId, clientSecret);
        if (!allowedClient) {
            JsonObject error = Json.createObjectBuilder()
                    .add("error", "invalid_client")
                    .add("error_description", "submitted client is nott allowed to use this grant type")
                    .build();
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(error).build();
        }

        String username = params.username;
        String password = params.password;

        SessionOpenResponse sessionOpenResponse = sessionService.login(username, password);
        if (sessionOpenResponse.isOk()) {
            String accessToken = tokenGenerator.generateFrom(sessionOpenResponse);
            Long expiresIn = configProvider.getAuthExpiryTime();

            JsonObject response = Json.createObjectBuilder()
                    .add("token_type", "bearer")
                    .add("access_token", accessToken)
                    .add("expires_in", expiresIn)
                    .build();

            return Response.ok(response)
                    .header(HttpHeaders.CACHE_CONTROL, "no-store")
                    .header("Pragma", "no-cache")
                    .build();
        } else {
            JsonObject error = Json.createObjectBuilder()
                    .add("error", "invalid_grant")
                    .add("error_description", "identity portal rejected user")
                    .build();
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity(error)
                    .build();
        }
    }

    private static String[] extractFromAuthHeader(String authHeader) {
        String[] spllitted = authHeader.split(" ", 2);
        spllitted[0] = new String(Base64.decode(spllitted[1]), StandardCharsets.UTF_8);
        return spllitted[0].split(":", 2);
    }

    private boolean checkAllowedClients(String clientId, String clientSecret) {
        if (configProvider.getAllowedClients().isEmpty())
            return true;

        String[] allowedClients = configProvider.getAllowedClients().split(",");
        for (String allowedClient : allowedClients) {
            if (allowedClient.equals(clientId + ":" + clientSecret))
                return true;
        }
        return false;
    }
}
