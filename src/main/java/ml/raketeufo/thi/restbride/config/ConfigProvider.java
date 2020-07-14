package ml.raketeufo.thi.restbride.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfigProvider {
    private static final String DUMMY_VALUE = "##dummy##";

    @ConfigProperty(name = "bridge.backend.url")
    String backendurl;

    @ConfigProperty(name = "bridge.token.issuer")
    String issuer;

    @ConfigProperty(name = "bridge.token.privatekey.location")
    String privateKey;

    @ConfigProperty(name = "bridge.token.privatekey.resource", defaultValue = "false")
    Boolean privateKeyResource;

    @ConfigProperty(name = "bridge.token.groups")
    String groupsString;

    @ConfigProperty(name = "bridge.token.auth.expirytime")
    Long authExpiryTime;

    @ConfigProperty(name = "bridge.oauth.allowedclients", defaultValue = DUMMY_VALUE)
    String allowedClients;

    public String getBackendurl() {
        return backendurl;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public String getGroupsString() {
        return groupsString;
    }

    public Long getAuthExpiryTime() {
        return authExpiryTime;
    }

    public String getAllowedClients() {
        if (DUMMY_VALUE.equals(allowedClients))
            return "";
        return allowedClients;
    }

    public boolean isPrivateKeyResource() {
        return privateKeyResource;
    }
}
