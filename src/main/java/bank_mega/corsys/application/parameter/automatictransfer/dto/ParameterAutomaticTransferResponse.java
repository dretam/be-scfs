package bank_mega.corsys.application.parameter.automatictransfer.dto;

import lombok.Builder;

@Builder
public record ParameterAutomaticTransferResponse(
        String code,
        String value
) {
}
