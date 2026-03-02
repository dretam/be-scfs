package bank_mega.corsys.infrastructure.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ldap")
public class LDAPProperties {
    private String url;
    private String host;
    private String clientSecret;
    private String aesKey;
    private String partnerId;
    private String channelId;
    private String encryptType;

    private String hostUrl;
    private String tokenUsername;
    private String tokenPassword;
    private String clientId;
}
