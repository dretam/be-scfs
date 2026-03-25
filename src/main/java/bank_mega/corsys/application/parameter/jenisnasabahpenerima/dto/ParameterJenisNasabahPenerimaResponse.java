package bank_mega.corsys.application.parameter.jenisnasabahpenerima.dto;

import lombok.Builder;

@Builder
public record ParameterJenisNasabahPenerimaResponse(
        Integer code,
        String value
) {
}
