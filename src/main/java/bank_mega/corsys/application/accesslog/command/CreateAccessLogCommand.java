package bank_mega.corsys.application.accesslog.command;

import bank_mega.corsys.domain.model.user.User;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CreateAccessLogCommand(
        User user,
        String httpMethod,
        String uri,
        String queryParams,
        String requestBody,
        int statusCode,
        String errorMessage,
        long responseTimeMs,
        String ipAddress,
        String userAgent,
        Instant createdAt
) {
}
