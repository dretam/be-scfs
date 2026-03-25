package bank_mega.corsys.application.parameter.sumberdana.dto;

import lombok.Builder;

@Builder
public record ParameterSumberDanaResponse(
        String code,
        String value
) {
}
