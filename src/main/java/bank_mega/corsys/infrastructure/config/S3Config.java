package bank_mega.corsys.infrastructure.config;

import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.s3.S3ClientBuilder;
import software.amazon.awssdk.services.s3.S3Configuration;


@Configuration
@RequiredArgsConstructor
public class S3Config {

    private final S3ConfigProperties s3ConfigProperties;

    private S3Client s3Client;

    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsCredentials = AwsBasicCredentials.create(
                s3ConfigProperties.getAccessKeyId(),
                s3ConfigProperties.getSecretAccessKey()
        );

        S3ClientBuilder builder = S3Client.builder()
                .credentialsProvider(StaticCredentialsProvider.create(awsCredentials))
                .region(Region.of(s3ConfigProperties.getRegion()))
                .serviceConfiguration(
                        S3Configuration.builder()
                                .pathStyleAccessEnabled(true)
                                .build()
                );

        if (s3ConfigProperties.getEndpoint() != null &&
            !s3ConfigProperties.getEndpoint().isEmpty()) {
            builder.endpointOverride(
                    java.net.URI.create(s3ConfigProperties.getEndpoint())
            );
        }

        s3Client = builder.build();
        return s3Client;
    }

    @PreDestroy
    public void destroy() {
        if (s3Client != null) {
            s3Client.close();
        }
    }
}