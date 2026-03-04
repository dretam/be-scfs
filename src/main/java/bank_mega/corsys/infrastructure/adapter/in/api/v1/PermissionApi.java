package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.PermissionAssembler;
import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.permission.command.CreatePermissionCommand;
import bank_mega.corsys.application.permission.command.SoftDeletePermissionCommand;
import bank_mega.corsys.application.permission.command.UpdatePermissionCommand;
import bank_mega.corsys.application.permission.dto.PermissionResponse;
import bank_mega.corsys.application.permission.usecase.*;
import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.infrastructure.config.security.HasPermission;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Permissions")
@RestController
@RequestMapping("/api/v1/permissions")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class PermissionApi {

    private final PagePermissionUseCase pagePermissionUseCase;
    private final RetrievePermissionUseCase retrievePermissionUseCase;
    private final CreatePermissionUseCase createPermissionUseCase;
    private final UpdatePermissionUseCase updatePermissionUseCase;
    private final SoftDeletePermissionUseCase softDeletePermissionUseCase;
    private final DeletePermissionUseCase deletePermissionUseCase;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("PERMISSION_READ")
    public ReadListResponse<List<PermissionResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort
    ) {
        Page<@NonNull PermissionResponse> pageable = this.pagePermissionUseCase.execute(page, perPage, sort, filter);
        List<PermissionResponse> data = pageable.getContent();
        return ReadListResponse.<List<PermissionResponse>>builder()
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
    @HasPermission("PERMISSION_READ")
    public ReadRetrieveResponse<PermissionResponse> retrieve(@PathVariable Long id) {
        PermissionResponse data = this.retrievePermissionUseCase.execute(id);
        return ReadRetrieveResponse.<PermissionResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("PERMISSION_CREATE")
    public ReadRetrieveResponse<PermissionResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid CreatePermissionCommand command
    ) {
        PermissionResponse data = this.createPermissionUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<PermissionResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("PERMISSION_UPDATE")
    public ReadRetrieveResponse<PermissionResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdatePermissionCommand command
    ) {
        PermissionResponse data = this.updatePermissionUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<PermissionResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("PERMISSION_DELETE")
    public ReadRetrieveResponse<PermissionResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid SoftDeletePermissionCommand command
    ) {
        PermissionResponse data = this.softDeletePermissionUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<PermissionResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            path = "/{id}/destroy",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("PERMISSION_DELETE")
    public DeleteResponse<PermissionId> delete(@PathVariable Long id) {
        PermissionId pk = this.deletePermissionUseCase.execute(id);
        return DeleteResponse.<PermissionId>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

}
