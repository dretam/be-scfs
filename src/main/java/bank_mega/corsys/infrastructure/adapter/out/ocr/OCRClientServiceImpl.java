package bank_mega.corsys.infrastructure.adapter.out.ocr;

import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.ocr.OCRData;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.port.OCRService;
import bank_mega.corsys.domain.port.OpenApiService;
import bank_mega.corsys.infrastructure.config.OCRProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OCRClientServiceImpl implements OCRService {

    private final OCRProperties ocrProperties;
    private final OpenApiService openApiService;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<OCRData> upload(byte[] fileBytes, String filename, UserId userId) {

        String accessToken = openApiService.getAccessToken();

        String timestamp = getISODateTime();
        String externalId = String.valueOf(System.currentTimeMillis());

        try {

            log.info("========== OCR UPLOAD START ==========");
            log.info("Filename        : {}", filename);
            log.info("File Size       : {} bytes", fileBytes.length);
            log.info("Timestamp       : {}", timestamp);

            // 1️⃣ Headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.set("Authorization", accessToken);
            headers.set("X-TIMESTAMP", timestamp);
            headers.set("X-PARTNER-ID", ocrProperties.getPartnerId());
            headers.set("X-EXTERNAL-ID", externalId);
            headers.set("CHANNEL-ID", ocrProperties.getChannelId());
            headers.set("API-VERSION", ocrProperties.getApiVersion());
            headers.set("Host", ocrProperties.getHost());

            // 2️⃣ Multipart Body
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

            ByteArrayResource fileResource = new ByteArrayResource(fileBytes) {
                @Override
                public String getFilename() {
                    return filename;
                }
            };

            body.add("file", fileResource);

            HttpEntity<MultiValueMap<String, Object>> request =
                    new HttpEntity<>(body, headers);

            // 3️⃣ Call OCR
            ResponseEntity<String> response = restTemplate.exchange(
                    ocrProperties.getUrl(),
                    HttpMethod.POST,
                    request,
                    String.class
            );


            return parseResponse(response.getBody(), userId);

        } catch (HttpStatusCodeException e) {

            log.error("========== OCR HTTP ERROR ==========");
            log.error("Status Code     : {}", e.getStatusCode());

            log.error("Response Headers:");
            if (e.getResponseHeaders() != null) {
                e.getResponseHeaders().forEach((key, value) ->
                        log.error("  {} : {}", key, value)
                );
            }

            log.error("Response Body   : {}", e.getResponseBodyAsString());
            throw new RuntimeException("OCR upload failed", e);

        } catch (ResourceAccessException e) {

            log.error("========== OCR CONNECTION ERROR ==========");
            log.error("Message         : {}", e.getMessage());

            throw new RuntimeException("OCR connection failed", e);

        } catch (Exception e) {

            log.error("========== OCR UNKNOWN ERROR ==========");
            log.error("Message         : {}", e.getMessage());

            throw new RuntimeException("OCR upload failed", e);
        }
    }

    private List<OCRData> parseResponse(String responseBody, UserId userId) throws Exception {

        JsonNode root = objectMapper.readTree(responseBody);
        JsonNode dataNode = root.path("data");

        List<OCRData> results = new ArrayList<>();

        if (dataNode.isArray()) {
            for (JsonNode node : dataNode) {

                OCRData result = OCRData.builder()
                        .atasNama(node.path("atas_nama").asText())
                        .nominal(node.path("nominal").asText())
                        .jangkaWaktu(node.path("jangka_waktu").asText())
                        .periode(node.path("periode").asText())
                        .rate(node.path("rate").asText())
                        .alokasi(node.path("alokasi").asText())
                        .namaRekeningTujuanPencairan(node.path("nama_rekening_tujuan_pencairan").asText())
                        .nomorRekeningTujuanPencairan(node.path("nomor_rekening_tujuan_pencairan").asText())
                        .nomorRekeningPengirim(node.path("nomor_rekening_pengirim").asText())
                        .nomorRekeningPlacement(node.path("nomor_rekening_placement").asText())
                        .audit(AuditTrail.create(userId.value()))
                        .build();

                results.add(result);
            }
        }

        return results;
    }

    public static String getISODateTime() {
        return ZonedDateTime.of(LocalDateTime.now(),
                ZoneId.of("Asia/Jakarta")).format(DateTimeFormatter.ofPattern("yyyy-MM dd'T'HH:mm:ssXXX"));
    }
}
