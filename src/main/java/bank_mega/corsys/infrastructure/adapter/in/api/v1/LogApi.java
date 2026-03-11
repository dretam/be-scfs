package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.AccessLogAssembler;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.accesslog.dto.AccessLogResponse;
import bank_mega.corsys.application.accesslog.usecase.PageAccessLogUseCase;
import bank_mega.corsys.application.accesslog.usecase.RetrieveAccessLogUseCase;
import bank_mega.corsys.domain.model.accesslog.AccessLog;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Logs")
@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class LogApi {

    private final PageAccessLogUseCase pageAccessLogUseCase;
    private final RetrieveAccessLogUseCase retrieveAccessLogUseCase;
    private final AccessLogAssembler accessLogAssembler;

    @GetMapping(
            path = "/access",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadListResponse<List<AccessLogResponse>> pageListAccessLog(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        Page<@NonNull AccessLog> pageable = this.pageAccessLogUseCase.execute(
                page,
                perPage,
                ParserUtil.expandParse(expand),
                sort,
                filter
        );
        List<AccessLogResponse> data = pageable.stream()
                .map(domainEntity -> accessLogAssembler.toResponse(domainEntity, ParserUtil.expandParse(expand)))
                .toList();
        return ReadListResponse.<List<AccessLogResponse>>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .pagination(
                        PaginationResponse.builder()
                                .currentPage(pageable.getNumber() + 1)
                                .totalPage(pageable.getTotalPages())
                                .perPage(pageable.getSize())
                                .total(pageable.getTotalElements())
                                .count(pageable.getNumberOfElements())
                                .hasNext(pageable.hasNext())
                                .hasPrevious(pageable.hasPrevious())
                                .hasContent(pageable.hasContent())
                                .build()
                )
                .build();
    }

    @GetMapping(
            path = "/access/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<AccessLogResponse> retrieve(
            @PathVariable Long id,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        AccessLog data = this.retrieveAccessLogUseCase.execute(id, ParserUtil.expandParse(expand));
        return ReadRetrieveResponse.<AccessLogResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(accessLogAssembler.toResponse(data))
                .build();
    }

}
