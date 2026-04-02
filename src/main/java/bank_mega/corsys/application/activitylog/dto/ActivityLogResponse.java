package bank_mega.corsys.application.activitylog.dto;

import bank_mega.corsys.application.user.dto.UserResponse;
import bank_mega.corsys.domain.model.activitylog.ActivityLogHttpMethod;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record ActivityLogResponse(
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
        ActivityLogHttpMethod httpMethod,
        Instant createdAt
) {
}
