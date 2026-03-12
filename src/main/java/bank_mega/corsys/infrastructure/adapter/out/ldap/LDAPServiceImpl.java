package bank_mega.corsys.infrastructure.adapter.out.ldap;

import bank_mega.corsys.domain.model.ldap.LDAPResponse;
import bank_mega.corsys.domain.port.LDAPService;
import bank_mega.corsys.infrastructure.config.LDAPProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.*;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.TimeZone;


@Service
@Slf4j
@RequiredArgsConstructor
public class LDAPServiceImpl implements LDAPService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final LDAPProperties ldapProperties;

    @Override
    public LDAPResponse verifyPassword(String nip, String pass) {
        try {
            // 1. Get Bearer Token
            String token = getToken();
            if (token == null) {
                log.error("Failed to obtain access token");
                return LDAPResponse.builder().verified(false).build();
            }

            // 2. Encrypt password using AES/GCM/NoPadding
            String encryptedPassword = encryptGCMLdap(pass, ldapProperties.getAesKey());

            // 4. Construct URL
            String url = String.format("%s/verifypassword/userid/%s/password/%s",
                    ldapProperties.getUrl(), nip, encryptedPassword);

            // 5. Generate timestamp (ISO8601)
            String timestamp = generateIso8601Timestamp();

            // 6. Generate signature
            String signature = generateSignature(token, "{}", url, "GET",
                    ldapProperties.getClientSecret(), timestamp);

            // 7. Generate external ID (unique numeric)
            String externalId = String.valueOf(System.currentTimeMillis()).substring(0, 12);

            // 8. Prepare headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", token);
            headers.set("X-SIGNATURE", signature);
            headers.set("X-TIMESTAMP", timestamp);
            headers.set("X-PARTNER-ID", ldapProperties.getPartnerId());
            headers.set("X-EXTERNAL-ID", externalId);
            headers.set("CHANNEL-ID", ldapProperties.getChannelId());
            headers.set("X-ENCRYPT-VERSION", ldapProperties.getEncryptType());
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Host", ldapProperties.getHost());

            log.debug("Request URL: {}", url);
            log.debug("Headers: {}", headers);

            // 9. Execute request
            HttpEntity<String> entity = new HttpEntity<>("{}", headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

            log.info(response.getBody());
            // 10. Parse response
            JsonNode body = objectMapper.readTree(response.getBody());
            String responseCode = body.path("responseCode").asText();
            String responseMessage = body.path("responseDescription").asText();

            log.info("LDAP Response - Code: {}, Message: {}", responseCode, responseMessage);

            boolean verified = "00".equals(responseCode);

            return LDAPResponse.builder()
                    .username(nip)
                    .verified(verified)
                    .build();

        } catch (Exception e) {
            log.error("Error verifying password for user {}: {}", nip, e.getMessage(), e);
            return LDAPResponse.builder().verified(false).build();
        }
    }

    /**
     * Encrypt password using AES/GCM/NoPadding
     * Specification:
     * - Algorithm: AES/GCM/NoPadding
     * - Key: 32 bytes
     * - IV: 12 bytes (random)
     * - Tag: 16 bytes (128 bits)
     * - Output: Base64[(IV 12 bytes) + Ciphertext + (Tag 16 bytes)]
     */
    public static String encryptGCMLdap(String plainText, String encSecret) throws Exception {
        try {
            // Generate random IV (12 bytes for GCM)
            byte[] iv = new byte[12];
            SecureRandom secureRandom = new SecureRandom();
            secureRandom.nextBytes(iv);

            // Prepare GCM parameters (128-bit authentication tag)
            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);

            // Prepare AES key
            Key key = new SecretKeySpec(encSecret.getBytes(StandardCharsets.UTF_8), "AES");

            // Initialize cipher
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, key, gcmSpec);

            // Encrypt
            byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

            // Combine: IV (12 bytes) + Ciphertext + Tag (16 bytes included in encryptedBytes)
            byte[] combinedIvAndCipherText = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, combinedIvAndCipherText, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, combinedIvAndCipherText, iv.length, encryptedBytes.length);

            // Encode to Base64
            String result = Base64.getEncoder().encodeToString(combinedIvAndCipherText);
            log.debug("Encrypted password length: {} bytes", combinedIvAndCipherText.length);

            return result;

        } catch (NoSuchAlgorithmException e) {
            throw new Exception("AES algorithm not available: " + e.getMessage());
        } catch (NoSuchPaddingException e) {
            throw new Exception("GCM padding not available: " + e.getMessage());
        } catch (InvalidKeyException e) {
            throw new Exception("Invalid encryption key: " + e.getMessage());
        } catch (InvalidAlgorithmParameterException e) {
            throw new Exception("Invalid GCM parameters: " + e.getMessage());
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new Exception("Encryption failed: " + e.getMessage());
        }
    }

    /**
     * Generate ISO8601 timestamp with timezone
     * Format: YYYY-MM-DDThh:mm:ss.sssTZD
     */
    private String generateIso8601Timestamp() {
        return ZonedDateTime.now(TimeZone.getTimeZone("Asia/Jakarta").toZoneId())
                .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    /**
     * Minify JSON by removing whitespace
     */
    private String minifyJson(String json) throws Exception {
        JsonNode node = objectMapper.readTree(json);
        return objectMapper.writeValueAsString(node);
    }

    /**
     * Generate X-SIGNATURE header
     * Formula: Base64(HMAC_SHA512(stringToSign, Client Secret))
     *
     * stringToSign = HTTPMethod + ":" + EndpointPath + ":" + AccessToken + ":" +
     *                Lowercase(HexEncode(SHA-256(minify(RequestBody)))) + ":" + TimeStamp
     */
    private String generateSignature(
            String bearer,
            String body,
            String url,
            String method,
            String clientSecret,
            String timestamp
    ) throws Exception {

        // Extract access token from Bearer
        String token = bearer.replace("Bearer ", "").trim();

        // Handle empty body
        if (body == null || body.trim().isEmpty()) {
            body = "{}";
        } else {
            body = minifyJson(body);
        }

        // Hash the body
        String bodyHash = hashSHA256(body);

        // Extract path from URL
        String path = URI.create(url).getPath();

        // Construct string to sign
        String stringToSign = method.toUpperCase() + ":" +
                              path + ":" +
                              token + ":" +
                              bodyHash + ":" +
                              timestamp;

        log.debug("String to sign: {}", stringToSign);

        // Generate HMAC-SHA512
        byte[] hmac = hmacSHA512(stringToSign, clientSecret);

        // Encode to Base64
        return Base64.getEncoder().encodeToString(hmac);
    }

    /**
     * SHA-256 hash and convert to lowercase hex
     */
    private String hashSHA256(String input) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(hash).toLowerCase();
    }

    /**
     * HMAC-SHA512
     */
    private byte[] hmacSHA512(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
        Mac mac = Mac.getInstance("HmacSHA512");
        mac.init(secretKey);
        return mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Convert bytes to hex string
     */
    private String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder();
        for (byte b : bytes) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString();
    }

    /**
     * Get OAuth2 Bearer token from Keycloak
     */
    private String getToken() {
        try {
            String url = ldapProperties.getHostUrl() + "/realms/quarkus/protocol/openid-connect/token";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Host", ldapProperties.getHost());

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("grant_type", "password");
            body.add("username", ldapProperties.getTokenUsername());
            body.add("password", ldapProperties.getTokenPassword());
            body.add("client_id", ldapProperties.getClientId());
            body.add("client_secret", ldapProperties.getClientSecret());

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

            JsonNode json = objectMapper.readTree(response.getBody());

            if (json.has("access_token")) {
                String accessToken = json.get("access_token").asText();
                log.debug("Access token obtained successfully");
                return "Bearer " + accessToken;
            }

            log.error("No access_token in response");
            return null;

        } catch (Exception e) {
            log.error("Failed to get token: {}", e.getMessage(), e);
            return null;
        }
    }
}
