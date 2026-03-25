package bank_mega.corsys.application.parameter.jenistransfer.dto;

import lombok.Builder;

@Builder
public record ParameterJenisTransferResponse(
        Integer code,
        String value
) {
}
