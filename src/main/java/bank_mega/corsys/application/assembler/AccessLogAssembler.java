package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.accesslog.dto.AccessLogResponse;
import bank_mega.corsys.domain.model.accesslog.AccessLog;

import java.util.Set;

public class AccessLogAssembler {

    public static AccessLogResponse toResponse(AccessLog saved) {
        if (saved == null) return null;
        return AccessLogResponse.builder()
                .id(saved.getId().value())
                .user(UserAssembler.toResponse(saved.getUser()))
                .ipAddress(saved.getIpAddress().value())
                .userAgent(saved.getUserAgent().value())
                .uri(saved.getUri().value())
                .queryParams(saved.getQueryParam().value())
                .requestBody(saved.getRequestBody().value())
                .statusCode(saved.getStatusCode().value())
                .responseTimeMs(saved.getResponseTimeMs().value())
                .errorMessage(saved.getErrorMessage().value())
                .httpMethod(saved.getHttpMethod())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    public static AccessLogResponse toResponse(AccessLog saved, Set<String> expands) {
        if (saved == null) return null;
        AccessLogResponse.AccessLogResponseBuilder builder = AccessLogResponse.builder()
                .id(saved.getId().value())
                .ipAddress(saved.getIpAddress().value())
                .userAgent(saved.getUserAgent().value())
                .uri(saved.getUri().value())
                .queryParams(saved.getQueryParam().value())
                .requestBody(saved.getRequestBody().value())
                .statusCode(saved.getStatusCode().value())
                .responseTimeMs(saved.getResponseTimeMs().value())
                .errorMessage(saved.getErrorMessage().value())
                .httpMethod(saved.getHttpMethod())
                .createdAt(saved.getCreatedAt())
                .createdAt(saved.getCreatedAt());
        if (expands.contains("user")) {
            builder = builder.user(UserAssembler.toResponse(saved.getUser()));
        }
        return builder.build();
    }

}
