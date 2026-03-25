package bank_mega.corsys.application.parameter.jenisperpanjangan.dto;

import lombok.Builder;

@Builder
public record ParameterJenisPerpanjanganResponse(
        String code,
        String value
) {
}
