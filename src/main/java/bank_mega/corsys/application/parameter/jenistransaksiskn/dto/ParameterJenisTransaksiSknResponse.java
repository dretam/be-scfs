package bank_mega.corsys.application.parameter.jenistransaksiskn.dto;

import lombok.Builder;

@Builder
public record ParameterJenisTransaksiSknResponse(
        Integer code,
        String value
) {
}
