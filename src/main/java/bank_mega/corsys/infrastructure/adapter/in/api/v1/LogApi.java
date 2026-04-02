package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.ActivityLogAssembler;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.activitylog.dto.ActivityLogResponse;
import bank_mega.corsys.application.activitylog.usecase.PageActivityLogUseCase;
import bank_mega.corsys.application.activitylog.usecase.RetrieveActivityLogUseCase;
import bank_mega.corsys.domain.model.activitylog.ActivityLog;
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
import java.util.UUID;


@Tag(name = "Logs")
@RestController
@RequestMapping("/api/v1/logs")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class LogApi {

    private final PageActivityLogUseCase pageActivityLogUseCase;
    private final RetrieveActivityLogUseCase retrieveActivityLogUseCase;
    private final ActivityLogAssembler activityLogAssembler;

    @GetMapping(
            path = "/activity",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadListResponse<List<ActivityLogResponse>> pageListActivityLog(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        Page<@NonNull ActivityLog> pageable = this.pageActivityLogUseCase.execute(
                page,
                perPage,
                ParserUtil.expandParse(expand),
                sort,
                filter
        );
        List<ActivityLogResponse> data = pageable.stream()
                .map(domainEntity -> activityLogAssembler.toResponse(domainEntity, ParserUtil.expandParse(expand)))
                .toList();
        return ReadListResponse.<List<ActivityLogResponse>>builder()
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
            path = "/activity/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<ActivityLogResponse> retrieve(
            @PathVariable UUID id,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        ActivityLog data = this.retrieveActivityLogUseCase.execute(id, ParserUtil.expandParse(expand));
        return ReadRetrieveResponse.<ActivityLogResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(activityLogAssembler.toResponse(data))
                .build();
    }

}
