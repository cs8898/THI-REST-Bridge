package ml.raketeufo.thi.restbride.entity.dto.request;

import org.eclipse.microprofile.openapi.annotations.media.Schema;

import javax.json.bind.annotation.JsonbProperty;

@Schema(name = "OAuth token Request Body", description = "OAauth Request Body for Password Grant https://www.oauth.com/oauth2-servers/access-tokens/password-grant/")
public class OAuthBody {
    @JsonbProperty("grant_type")
    @Schema(name = "grant_type", required = true, example = "password")
    public String grantType;

    @JsonbProperty("username")
    @Schema(name = "username", required = true, example = "userXYZ")
    public String username;

    @JsonbProperty("password")
    @Schema(name = "password", required = true, example = "1234luggage")
    public String password;

    @JsonbProperty("client_id")
    @Schema(name = "client_id", example = "xxxxxxxxx")
    public String clientId;

    @JsonbProperty("client_secret")
    @Schema(name = "client_secret", example = "xxxxxxxxx")
    public String clientSecret;
}
