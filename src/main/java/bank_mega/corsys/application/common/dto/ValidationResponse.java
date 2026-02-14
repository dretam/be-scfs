package bank_mega.corsys.application.common.dto;

import lombok.Builder;

@Builder
public record ValidationResponse<T>(
        Integer status,
        T message
) {
}
