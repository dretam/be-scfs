package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.BranchAssembler;
import bank_mega.corsys.application.branch.dto.BranchResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.branch.usecase.*;
import bank_mega.corsys.domain.model.branch.Branch;
import bank_mega.corsys.infrastructure.adapter.in.validation.branch.BranchIdExist;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "Branches")
@Validated
@RestController
@RequestMapping("/api/v1/branches")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class BranchApi {

    private final PageBranchUseCase pageBranchUseCase;
    private final RetrieveBranchUseCase retrieveBranchUseCase;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadListResponse<List<BranchResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<@NonNull Branch> pageable = this.pageBranchUseCase.execute(page, perPage, sort, filter);
        List<BranchResponse> data = pageable.stream()
                .map(BranchAssembler::toResponse)
                .toList();
        return ReadListResponse.<List<BranchResponse>>builder()
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
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<BranchResponse> retrieve(@PathVariable @NotNull @BranchIdExist Long id) {
        Branch data = this.retrieveBranchUseCase.execute(id);
        return ReadRetrieveResponse.<BranchResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(BranchAssembler.toResponse(data))
                .build();
    }

}
