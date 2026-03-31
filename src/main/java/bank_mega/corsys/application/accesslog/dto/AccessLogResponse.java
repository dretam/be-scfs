package bank_mega.corsys.application.accesslog.dto;

import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.domain.model.accesslog.AccessLogHttpMethod;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record AccessLogResponse(
        UUID id,
        UserResponse user,
        String ipAddress,
        String userAgent,
        String uri,
        String queryParams,
        String requestBody,
        Integer statusCode,
        Long responseTimeMs,
        String errorMessage,
        AccessLogHttpMethod httpMethod,
        Instant createdAt
) {
}
