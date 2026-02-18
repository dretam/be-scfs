package bank_mega.corsys.infrastructure.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "aws.s3")
public class S3ConfigProperties {
    private String accessKeyId;
    private String secretAccessKey;
    private String region;
    private String bucketName;
    private String endpoint;
}