package bank_mega.corsys.infrastructure.adapter.out.ocr;

import bank_mega.corsys.domain.model.ocr.OCRData;
import bank_mega.corsys.domain.repository.OCRClientRepository;
import bank_mega.corsys.infrastructure.config.OCRProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class OCRClientRepositoryImpl implements OCRClientRepository {

    private final OCRProperties ocrProperties;
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public List<OCRData> upload(byte[] fileBytes, String filename) {

        try {

            // 1️⃣ Generate Headers
            String timestamp = generateIso8601Timestamp();
            String externalId = String.valueOf(System.currentTimeMillis());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            headers.set("X-TIMESTAMP", timestamp);
            headers.set("X-PARTNER-ID", ocrProperties.getPartnerId());
            headers.set("X-EXTERNAL-ID", externalId);
            headers.set("CHANNEL-ID", ocrProperties.getChannelId());
            headers.set("API-VERSION", ocrProperties.getApiVersion());
            headers.set("Host", ocrProperties.getHost());

            // 2️⃣ Prepare Multipart Body
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

            // 3️⃣ Call OCR API
            ResponseEntity<String> response = restTemplate.exchange(
                    ocrProperties.getUrl(),
                    HttpMethod.POST,
                    request,
                    String.class
            );

            log.info("OCR Response: {}", response.getBody());

            return parseResponse(response.getBody());

        } catch (Exception e) {
            log.error("OCR upload failed", e);
            throw new RuntimeException("OCR upload failed", e);
        }
    }

    private List<OCRData> parseResponse(String responseBody) throws Exception {

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
                        .build();

                results.add(result);
            }
        }

        return results;
    }

    private String generateIso8601Timestamp() {
        return ZonedDateTime.now()
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }
}
