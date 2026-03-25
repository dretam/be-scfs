package bank_mega.corsys.application.parameter.statuskependudukanpenerima.dto;

import lombok.Builder;

@Builder
public record ParameterStatusKependudukanPenerimaResponse(
        Integer code,
        String value
) {
}
