package bank_mega.corsys.application.common.dto;

import lombok.Builder;

@Builder
public record ReadListResponse<T>(
        Integer status,
        String message,
        T data,
        PaginationResponse pagination
) {
}
