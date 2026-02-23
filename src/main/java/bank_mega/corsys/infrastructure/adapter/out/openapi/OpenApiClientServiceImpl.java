package bank_mega.corsys.infrastructure.adapter.out.openapi;

import bank_mega.corsys.domain.port.OpenApiService;
import bank_mega.corsys.infrastructure.config.OpenApiProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenApiClientServiceImpl implements OpenApiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final OpenApiProperties openApiProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();


    private String getAuthorization() {
        String credentials = openApiProperties.getClientId() + ":" +
                             openApiProperties.getClientSecret();

        String encoded = Base64.getEncoder()
                .encodeToString(credentials.getBytes());

        return "Basic " + encoded;
    }

    @Override
    public String getAccessToken() {

        long start = System.currentTimeMillis();

        try {

            String url = openApiProperties.getHostUrl()
                         + "/realms/bpsecurity/protocol/openid-connect/token";

            log.info("========== TOKEN REQUEST START ==========");
            log.info("Client ID        : {}", openApiProperties.getClientId());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Host", openApiProperties.getHost());
            headers.set("Authorization", getAuthorization());

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "client_credentials");

            HttpEntity<MultiValueMap<String, String>> entity =
                    new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            JsonNode json = objectMapper.readTree(response.getBody());

            if (json.has("access_token")) {
                String accessToken = json.get("access_token").asText();
                log.info("Access token obtained successfully");
                log.info("========== TOKEN REQUEST SUCCESS ==========");
                return "Bearer " + accessToken;
            }

            log.error("No access_token in response");
            return null;

        } catch (HttpStatusCodeException e) {

            log.error("========== TOKEN HTTP ERROR ==========");
            log.error("Status Code : {}", e.getStatusCode());
            log.error("Response Body:");
            return null;

        } catch (ResourceAccessException e) {
            log.error("========== TOKEN CONNECTION ERROR ==========");
            log.error("Message: {}", e.getMessage());
            return null;

        } catch (Exception e) {

            log.error("========== TOKEN UNKNOWN ERROR ==========");
            log.error("Message: {}", e.getMessage());
            return null;
        }
    }
}
