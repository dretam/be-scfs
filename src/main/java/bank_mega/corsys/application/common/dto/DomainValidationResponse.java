package bank_mega.corsys.application.common.dto;

import lombok.Builder;

@Builder
public record DomainValidationResponse(
        Integer status,
        String type,
        String message
) {
}
