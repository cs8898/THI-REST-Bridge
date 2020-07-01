package ml.raketeufo.thi.restbride.endpoints;

import ml.raketeufo.thi.restbride.backendcom.response.BaseResponse;
import ml.raketeufo.thi.restbride.backendcom.response.GradesResponse;
import ml.raketeufo.thi.restbride.backendcom.response.PersDataResponse;
import ml.raketeufo.thi.restbride.backendcom.response.TimetableResponse;
import ml.raketeufo.thi.restbride.backendcom.thiapp.ThiAppUserService;
import ml.raketeufo.thi.restbride.jwt.JWTClaims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@SecurityRequirement(name = "JWT AuthToken")
@RolesAllowed("user")
@RequestScoped
public class UserResource {

    @Inject
    ThiAppUserService thiAppUserService;

    @Inject
    JsonWebToken jwt;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = PersDataResponse.class)
            )
    )
    @Operation(summary = "User Information", description = "Fetches Informations belonging to User")
    public Response getUserInformation() {
        if (jwt.containsClaim(JWTClaims.sid.name())) {
            String session = jwt.getClaim(JWTClaims.sid.name());
            PersDataResponse response = thiAppUserService.getPersonalData(session);
            if (response.getStatus() == BaseResponse.Status.OK) {
                return Response.ok(response).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
            }
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @GET
    @Path("grades")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = GradesResponse.class)
            )
    )
    @Operation(summary = "Fetch Exams and Grades", description = "Fetches Exams and Grades for User")
    public Response getGrades() {
        if (jwt.containsClaim(JWTClaims.sid.name())) {
            String session = jwt.getClaim(JWTClaims.sid.name());
            GradesResponse response = thiAppUserService.getGrades(session);
            if (response.isOk()) {
                return Response.ok(response).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
            }
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }

    @GET
    @Path("timetable")
    @Produces(MediaType.APPLICATION_JSON)
    @APIResponse(
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = TimetableResponse.class)
            )
    )
    @Parameter(name = "details", description = "fetch datails for timetable (pruefung, ziel, inhalt, literatur)")
    @Operation(summary = "User Timetable", description = "Fetches the Timetable for the User")
    public Response getTimetable(@QueryParam("details") @DefaultValue("0") String detailsString) {
        if (jwt.containsClaim(JWTClaims.sid.name())) {
            int details = 0;
            try {
                details = Integer.parseInt(detailsString);
                if (details != 0) {
                    details = 1;
                }
            } catch (NumberFormatException ignore) {
            }
            String session = jwt.getClaim(JWTClaims.sid.name());
            TimetableResponse response = thiAppUserService.getTimetable(session, details);
            if (response.isOk()) {
                return Response.ok(response).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED).entity(response).build();
            }
        } else {
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
}
