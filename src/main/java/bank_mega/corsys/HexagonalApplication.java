package bank_mega.corsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import bank_mega.corsys.infrastructure.config.S3ConfigProperties;

@SpringBootApplication
@EnableConfigurationProperties(S3ConfigProperties.class)
public class HexagonalApplication {

    public static void main(String[] args) {
        SpringApplication.run(HexagonalApplication.class, args);
    }

}
