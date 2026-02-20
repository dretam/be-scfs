package bank_mega.corsys.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "openapi")
public class OpenApiProperties {
    private String host;
    private String hostUrl;
    private String clientId;
    private String clientSecret;
}
