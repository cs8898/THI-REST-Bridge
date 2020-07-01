package ml.raketeufo.thi.restbride;

import org.eclipse.microprofile.jwt.Claims;

public enum JWTClaims {
    sid("sid", String.class);

    private String description;
    private Class<?> type;

    JWTClaims(String description, Class<?> type) {
        this.description = description;
        this.type = type;
    }

    public String getDescription() {
        return this.description;
    }

    public Class<?> getType() {
        return this.type;
    }
}
