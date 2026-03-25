package bank_mega.corsys.application.parameter.approverbiayamaterai.dto;

import lombok.Builder;

@Builder
public record ParameterApproverBiayaMateraiResponse(
        String code,
        String value
) {
}
