package bank_mega.corsys.application.parameter.metodepembayaranbunga.dto;

import lombok.Builder;

@Builder
public record ParameterMetodePembayaranBungaResponse(
        String code,
        String value
) {
}
