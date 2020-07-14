package ml.raketeufo.thi.restbride.jwt;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import ml.raketeufo.thi.restbride.config.ConfigProvider;
import org.eclipse.microprofile.jwt.Claims;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

/**
 * Utilities for generating a JWT for testing
 */
@ApplicationScoped

public class TokenUtils {

    @Inject
    ConfigProvider configProvider;

    public TokenUtils() {
        // no-op: utility class
    }

    /**
     * Utility method to generate a JWT string from a JSON resource file that is signed by the privateKey.pem
     * test resource key, possibly with invalid fields.
     *
     * @param timeClaims - used to return the exp, iat, auth_time claims
     * @return the JWT string
     * @throws Exception on parse failure
     */
    public String generateTokenString(Map<String, Long> timeClaims, Map<String, Object> claimsMap)
            throws Exception {
        // Use the test private key associated with the test public key for a valid signature
        PrivateKey pk = readPrivateKey(configProvider.getPrivateKey());
        return generateTokenString(pk, configProvider.getPrivateKey(), timeClaims, claimsMap);
    }

    private String generateTokenString(PrivateKey privateKey, String kid,
                                       Map<String, Long> timeClaims, Map<String, Object> claimsMap) throws Exception {

        JwtClaimsBuilder claims = Jwt.claims(claimsMap);
        long currentTimeInSecs = currentTimeInSecs();
        long exp = timeClaims != null && timeClaims.containsKey(Claims.exp.name())
                ? timeClaims.get(Claims.exp.name()) : currentTimeInSecs + configProvider.getAuthExpiryTime();

        List<String> groups = Arrays.asList(configProvider.getGroupsString().split(","));

        claims.issuedAt(currentTimeInSecs);
        claims.claim(Claims.auth_time.name(), currentTimeInSecs);
        claims.claim(Claims.iss.name(), configProvider.getIssuer());
        claims.claim(Claims.groups.name(), groups);
        claims.expiresAt(exp);

        return claims.jws().signatureKeyId(kid).sign(privateKey);
    }

    /**
     * Read a PEM encoded private key from the classpath
     *
     * @param pemResName - key file resource name
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    public static PrivateKey readPrivateKey(final String pemResName) throws Exception {
        try (InputStream contentIS = TokenUtils.class.getResourceAsStream(pemResName)) {
            if (contentIS == null) {
                throw new RuntimeException(pemResName + " not accessable");
            }
            byte[] tmp = new byte[4096];
            int length = contentIS.read(tmp);
            return decodePrivateKey(new String(tmp, 0, length, StandardCharsets.UTF_8));
        }
    }

    /**
     * Decode a PEM encoded private key string to an RSA PrivateKey
     *
     * @param pemEncoded - PEM string for private key
     * @return PrivateKey
     * @throws Exception on decode failure
     */
    public static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
        byte[] encodedBytes = toEncodedBytes(pemEncoded);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    private static byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    private static String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    /**
     * @return the current time in seconds since epoch
     */
    public static int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }
}
