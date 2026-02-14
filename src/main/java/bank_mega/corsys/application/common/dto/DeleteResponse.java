package bank_mega.corsys.application.common.dto;

import lombok.Builder;

@Builder
public record DeleteResponse<T>(
        Integer status,
        String message,
        T id
) {
}
