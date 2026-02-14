package bank_mega.corsys.application.common.dto;

import lombok.Builder;

@Builder
public record PaginationResponse(
         Long total,
         Integer count,
         Integer currentPage,
         Integer perPage,
         Integer totalPage,
         Boolean hasNext,
         Boolean hasPrevious,
         Boolean hasContent
) {
}
