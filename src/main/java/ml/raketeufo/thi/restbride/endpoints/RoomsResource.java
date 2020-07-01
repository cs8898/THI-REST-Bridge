package ml.raketeufo.thi.restbride.endpoints;


import ml.raketeufo.thi.restbride.backendcom.response.RoomsResponse;
import ml.raketeufo.thi.restbride.backendcom.thiapp.ThiAppRoomsService;
import ml.raketeufo.thi.restbride.backendcom.thiapp.ThiAppUserService;
import ml.raketeufo.thi.restbride.entity.dto.response.BaseResponse;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonString;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/rooms")
@SecurityRequirement(name = "JWT AuthToken")
@RolesAllowed("user")
@RequestScoped
public class RoomsResource {

    @Inject
    ThiAppRoomsService thiAppRoomsService;

    private static final String JWT_CLAIM_SID = "sid";

    @Inject
    @Claim(JWT_CLAIM_SID)
    Optional<JsonString> jwtSession;

    @GET
    @Path("free")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Fetch unocupied Rooms", description = "Fetches all free rooms grouped by date")
    @APIResponse(
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = RoomsResponse.class)
            )
    )
    public Response getFreeRooms() {
        if (jwtSession.isPresent()) {
            String session = jwtSession.get().getString();
            return Response.ok(thiAppRoomsService.getRooms(session)).build();
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
