package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.InternalUserAssembler;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.domain.model.internaluser.InternalUser;
import bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.InternalUserNameExist;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.internaluser.dto.InternalUserResponse;
import bank_mega.corsys.application.internaluser.usecase.*;
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


@Tag(name = "InternalUsers")
@Validated
@RestController
@RequestMapping("/api/v1/internal-users")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class InternalUserApi {

    private final PageInternalUserUseCase pageInternalUserUseCase;
    private final RetrieveInternalUserUseCase retrieveInternalUserUseCase;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadListResponse<List<InternalUserResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        Page<@NonNull InternalUser> pageable = this.pageInternalUserUseCase.execute(
                page,
                perPage,
                ParserUtil.expandParse(expand),
                sort,
                filter
        );
        List<InternalUserResponse> data = pageable.stream()
                .map(domainEntity -> InternalUserAssembler.toResponse(domainEntity, ParserUtil.expandParse(expand)))
                .toList();
        return ReadListResponse.<List<InternalUserResponse>>builder()
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
            path = "/{userName}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<InternalUserResponse> retrieve(
            @PathVariable @NotNull @InternalUserNameExist String userName,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        InternalUser data = this.retrieveInternalUserUseCase.execute(userName, ParserUtil.expandParse(expand));
        return ReadRetrieveResponse.<InternalUserResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(InternalUserAssembler.toResponse(data))
                .build();
    }
}
