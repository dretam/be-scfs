package bank_mega.corsys.application.parameter.jenistransaksirtgs.dto;

import lombok.Builder;

@Builder
public record ParameterJenisTransaksiRtgsResponse(
        Integer code,
        String value
) {
}
