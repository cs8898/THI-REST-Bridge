package ml.raketeufo.thi.restbride.config;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConfigProvider {
    @ConfigProperty(name = "bridge.domain")
    String domain;

    @ConfigProperty(name = "bridge.backend.url")
    String backendurl;

    @ConfigProperty(name = "bridge.token.issuer")
    String issuer;

    @ConfigProperty(name = "bridge.token.privatekey.location")
    String privateKey;

    @ConfigProperty(name = "bridge.token.groups")
    String groupsString;

    @ConfigProperty(name = "bridge.token.auth.expirytime")
    Long authExpiryTime;

    public String getDomain() {
        return domain;
    }

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
}
