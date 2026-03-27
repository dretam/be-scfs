package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.role.command.AssignMenusCommand;
import bank_mega.corsys.application.role.command.AssignPermissionsCommand;
import bank_mega.corsys.application.role.command.CreateRoleCommand;
import bank_mega.corsys.application.role.command.SoftDeleteRoleCommand;
import bank_mega.corsys.application.role.command.UpdateRoleCommand;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.application.role.usecase.*;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.infrastructure.config.security.HasPermission;
import bank_mega.corsys.infrastructure.util.ParserUtil;
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


@Tag(name = "Roles")
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class RoleApi {

    private final PageRoleUseCase pageRoleUseCase;
    private final RetrieveRoleUseCase retrieveRoleUseCase;
    private final CreateRoleUseCase createRoleUseCase;
    private final UpdateRoleUseCase updateRoleUseCase;
    private final SoftDeleteRoleUseCase softDeleteRoleUseCase;
    private final DeleteRoleUseCase deleteRoleUseCase;
    private final AssignPermissionsToRoleUseCase assignPermissionsToRoleUseCase;
    private final RemoveAllPermissionsFromRoleUseCase removeAllPermissionsFromRoleUseCase;
    private final AssignMenusToRoleUseCase assignMenusToRoleUseCase;
    private final RemoveAllMenusFromRoleUseCase removeAllMenusFromRoleUseCase;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("ROLE_READ")
    public ReadListResponse<List<RoleResponse>> list(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        Page<@NonNull Role> pageable = this.pageRoleUseCase.execute(page, perPage, ParserUtil.expandParse(expand), sort, filter);
        List<RoleResponse> data = pageable.stream()
                .map(role -> RoleAssembler.toResponse(role, ParserUtil.expandParse(expand)))
                .toList();
        return ReadListResponse.<List<RoleResponse>>builder()
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
    @HasPermission("ROLE_READ")
    public ReadRetrieveResponse<RoleResponse> retrieve(
            @PathVariable String id,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        RoleResponse data = this.retrieveRoleUseCase.execute(id, ParserUtil.expandParse(expand));
        return ReadRetrieveResponse.<RoleResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("ROLE_CREATE")
    public ReadRetrieveResponse<RoleResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody CreateRoleCommand command
    ) {
        RoleResponse data = this.createRoleUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<RoleResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("ROLE_UPDATE")
    public ReadRetrieveResponse<RoleResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody UpdateRoleCommand command
    ) {
        RoleResponse data = this.updateRoleUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<RoleResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("ROLE_DELETE")
    public ReadRetrieveResponse<RoleResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody SoftDeleteRoleCommand command
    ) {
        RoleResponse data = this.softDeleteRoleUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<RoleResponse>builder()
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
    @HasPermission("ROLE_DELETE")
    public DeleteResponse<RoleCode> delete(@PathVariable String id) {
        RoleCode pk = this.deleteRoleUseCase.execute(id);
        return DeleteResponse.<RoleCode>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .id(pk)
                .build();
    }

    @PostMapping(
            path = "/{roleId}/permissions",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("ROLE_UPDATE")
    public ReadRetrieveResponse<RoleResponse> assignPermissions(
            @PathVariable String roleId,
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid AssignPermissionsCommand command
    ) {
        RoleResponse data = this.assignPermissionsToRoleUseCase.execute(
                new AssignPermissionsCommand(roleId, command.permissionIds()),
                authPrincipal
        );
        return ReadRetrieveResponse.<RoleResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            path = "/{roleId}/permissions",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("ROLE_UPDATE")
    public ReadRetrieveResponse<RoleResponse> removeAllPermissions(
            @PathVariable String roleId,
            @AuthenticationPrincipal User authPrincipal
    ) {
        RoleResponse data = this.removeAllPermissionsFromRoleUseCase.execute(roleId, authPrincipal);
        return ReadRetrieveResponse.<RoleResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PostMapping(
            path = "/{roleId}/menus",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("ROLE_UPDATE")
    public ReadRetrieveResponse<RoleResponse> assignMenus(
            @PathVariable String roleId,
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid AssignMenusCommand command
    ) {
        RoleResponse data = this.assignMenusToRoleUseCase.execute(
                new AssignMenusCommand(roleId, command.menuIds()),
                authPrincipal
        );
        return ReadRetrieveResponse.<RoleResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            path = "/{roleId}/menus",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("ROLE_UPDATE")
    public ReadRetrieveResponse<RoleResponse> removeAllMenus(
            @PathVariable String roleId,
            @AuthenticationPrincipal User authPrincipal
    ) {
        RoleResponse data = this.removeAllMenusFromRoleUseCase.execute(roleId, authPrincipal);
        return ReadRetrieveResponse.<RoleResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

}
