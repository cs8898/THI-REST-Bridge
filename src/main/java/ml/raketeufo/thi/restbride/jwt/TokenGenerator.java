package ml.raketeufo.thi.restbride.jwt;

import ml.raketeufo.thi.restbride.backendcom.response.SessionOpenResponse;
import ml.raketeufo.thi.restbride.config.ConfigProvider;
import org.eclipse.microprofile.jwt.Claims;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.HashMap;

@ApplicationScoped
public class TokenGenerator {

    @Inject
    TokenUtils tokenUtils;

    public TokenGenerator() {
    }

    public String generateFrom(SessionOpenResponse openResponse) {
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(JWTClaims.sid.name(), openResponse.getSession());
        claims.put(Claims.sub.name(), openResponse.getUsername());

        try {
            return tokenUtils.generateTokenString(null, claims);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
