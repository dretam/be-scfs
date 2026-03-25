package bank_mega.corsys.application.parameter.metodepembayaranpokok.dto;

import lombok.Builder;

@Builder
public record ParameterMetodePembayaranPokokResponse(
        String code,
        String value
) {
}
