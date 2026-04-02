package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.activitylog.dto.ActivityLogResponse;
import bank_mega.corsys.domain.model.activitylog.ActivityLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ActivityLogAssembler {

    private final UserAssembler userAssembler;

    public ActivityLogResponse toResponse(ActivityLog saved) {
        return toResponse(saved, null);
    }

    public ActivityLogResponse toResponse(ActivityLog saved, Set<String> expands) {
        if (saved == null) return null;
        ActivityLogResponse.ActivityLogResponseBuilder builder = ActivityLogResponse.builder()
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
        if (expands != null && expands.contains("user")) {
            builder = builder.user(userAssembler.toResponse(saved.getUser()));
        }
        return builder.build();
    }

}
