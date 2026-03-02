package bank_mega.corsys.application.common.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record DomainValidationResponse(
        Integer status,
        String type,
        String message,
        Instant timestamp
) {
}
