package bank_mega.corsys.infrastructure.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "ocr")
public class OCRProperties {
    private String url;
    private String partnerId;
    private String channelId;
    private String apiVersion;
    private String host;
}
