package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.CommunityAssembler;
import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.community.command.CreateCommunityCommand;
import bank_mega.corsys.application.community.command.SoftDeleteCommunityCommand;
import bank_mega.corsys.application.community.command.UpdateCommunityCommand;
import bank_mega.corsys.application.community.dto.CommunityResponse;
import bank_mega.corsys.application.community.usecase.*;
import bank_mega.corsys.domain.model.community.Community;
import bank_mega.corsys.domain.model.community.CommunityId;
import bank_mega.corsys.domain.model.user.User;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "Communities")
@RestController
@RequestMapping("/api/v1/communities")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class CommunityApi {

    private final PageCommunityUseCase pageCommunityUseCase;
    private final RetrieveCommunityUseCase retrieveCommunityUseCase;
    private final CreateCommunityUseCase createCommunityUseCase;
    private final UpdateCommunityUseCase updateCommunityUseCase;
    private final SoftDeleteCommunityUseCase softDeleteCommunityUseCase;
    private final DeleteCommunityUseCase deleteCommunityUseCase;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadListResponse<List<CommunityResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<@NonNull Community> pageable = this.pageCommunityUseCase.execute(page, perPage, sort, filter);
        List<CommunityResponse> data = pageable.stream()
                .map(CommunityAssembler::toResponse)
                .toList();
        return ReadListResponse.<List<CommunityResponse>>builder()
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
    public ReadRetrieveResponse<CommunityResponse> retrieve(@PathVariable UUID id) {
        CommunityResponse data = this.retrieveCommunityUseCase.execute(id);
        return ReadRetrieveResponse.<CommunityResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<CommunityResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody CreateCommunityCommand command
    ) {
        CommunityResponse data = this.createCommunityUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<CommunityResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<CommunityResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody UpdateCommunityCommand command
    ) {
        CommunityResponse data = this.updateCommunityUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<CommunityResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ReadRetrieveResponse<CommunityResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody SoftDeleteCommunityCommand command
    ) {
        CommunityResponse data = this.softDeleteCommunityUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<CommunityResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            path = "/{id}/destroy",
            consumes = "*/*",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public DeleteResponse<CommunityId> delete(@PathVariable UUID id) {
        CommunityId pk = this.deleteCommunityUseCase.execute(id);
        return DeleteResponse.<CommunityId>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
