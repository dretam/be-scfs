package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.assembler.RoleAssembler;
import bank_mega.corsys.application.assembler.RoleChildrenAssembler;
import bank_mega.corsys.application.common.dto.DeleteResponse;
import bank_mega.corsys.application.common.dto.PaginationResponse;
import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.role.command.*;
import bank_mega.corsys.application.role.dto.RoleResponse;
import bank_mega.corsys.application.role.usecase.*;
import bank_mega.corsys.application.rolechildren.command.CreateRoleChildrenCommand;
import bank_mega.corsys.application.rolechildren.command.SoftDeleteRoleChildrenCommand;
import bank_mega.corsys.application.rolechildren.command.UpdateRoleChildrenCommand;
import bank_mega.corsys.application.rolechildren.dto.RoleChildrenResponse;
import bank_mega.corsys.application.rolechildren.usecase.*;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenCode;
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


@Tag(name = "RoleChildren")
@RestController
@RequestMapping("/api/v1/roleChildren")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class RoleChildrenApi {

    private final PageRoleChildrenUseCase pageRoleUseCase;
    private final RetrieveRoleChildrenUseCase retrieveRoleChildrenUseCase;
    private final CreateRoleChildrenUseCase createRoleChildrenUseCase;
    private final UpdateRoleChildrenUseCase updateRoleChildrenUseCase;
    private final SoftDeleteRoleChildrenUseCase softDeleteRoleChildrenUseCase;
    private final DeleteRoleChildrenUseCase deleteRoleChildrenUseCase;
    private final AssignPermissionsToRoleChildrenUseCase assignPermissionsToRoleChildrenUseCase;
    private final RemoveAllPermissionsFromRoleChildrenUseCase removeAllPermissionsFromRoleChildrenUseCase;
    private final AssignMenusToRoleChildrenUseCase assignMenusToRoleChildrenUseCase;
    private final RemoveAllMenusFromRoleChildrenUseCase removeAllMenusFromRoleChildrenUseCase;

    @GetMapping(
            value = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("ROLE_READ")
    public ReadListResponse<List<RoleChildrenResponse>> roleChildrenList(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "perPage", required = false, defaultValue = "5") int perPage,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        Page<@NonNull RoleChildren> pageable = this.pageRoleUseCase.execute(page, perPage, ParserUtil.expandParse(expand),
                sort, filter);
        List<RoleChildrenResponse> data = pageable.stream()
                .map(roleChildren -> RoleChildrenAssembler.toResponse(roleChildren, ParserUtil.expandParse(expand)))
                .toList();
        return ReadListResponse.<List<RoleChildrenResponse>>builder()
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
            value = "/one",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("ROLE_READ")
    public ReadRetrieveResponse<RoleChildrenResponse> retrieveRoleChildren(
            @RequestParam String id,
            @RequestParam String parentId,
            @RequestParam(value = "expands", required = false) String expand
    ) {
        RoleChildrenResponse data = this.retrieveRoleChildrenUseCase.execute(id, parentId, ParserUtil.expandParse(expand));
        return ReadRetrieveResponse.<RoleChildrenResponse>builder()
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
    public ReadRetrieveResponse<RoleChildrenResponse> create(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody CreateRoleChildrenCommand command
    ) {
        RoleChildrenResponse data = this.createRoleChildrenUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<RoleChildrenResponse>builder()
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
    public ReadRetrieveResponse<RoleChildrenResponse> update(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody UpdateRoleChildrenCommand command
    ) {
        RoleChildrenResponse data = this.updateRoleChildrenUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<RoleChildrenResponse>builder()
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
    public ReadRetrieveResponse<RoleChildrenResponse> softDelete(
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody SoftDeleteRoleChildrenCommand command
    ) {
        RoleChildrenResponse data = this.softDeleteRoleChildrenUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<RoleChildrenResponse>builder()
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
    public DeleteResponse<RoleChildrenCode> delete(@PathVariable String id) {
        RoleChildrenCode pk = this.deleteRoleChildrenUseCase.execute(id);
        return DeleteResponse.<RoleChildrenCode>builder()
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
    public ReadRetrieveResponse<RoleChildrenResponse> assignPermissions(
            @PathVariable String roleId,
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid AssignPermissionsCommand command
    ) {
        RoleChildrenResponse data = this.assignPermissionsToRoleChildrenUseCase.execute(
                new AssignPermissionsCommand(roleId, command.permissionIds()),
                authPrincipal
        );
        return ReadRetrieveResponse.<RoleChildrenResponse>builder()
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
    public ReadRetrieveResponse<RoleChildrenResponse> removeAllPermissions(
            @PathVariable String roleId,
            @AuthenticationPrincipal User authPrincipal
    ) {
        RoleChildrenResponse data = this.removeAllPermissionsFromRoleChildrenUseCase.execute(roleId, authPrincipal);
        return ReadRetrieveResponse.<RoleChildrenResponse>builder()
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
    public ReadRetrieveResponse<RoleChildrenResponse> assignMenus(
            @PathVariable String roleId,
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid AssignMenusCommand command
    ) {
        RoleChildrenResponse data = this.assignMenusToRoleChildrenUseCase.execute(
                new AssignMenusCommand(roleId, command.menuIds()),
                authPrincipal
        );
        return ReadRetrieveResponse.<RoleChildrenResponse>builder()
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
    public ReadRetrieveResponse<RoleChildrenResponse> removeAllMenus(
            @PathVariable String roleId,
            @AuthenticationPrincipal User authPrincipal
    ) {
        RoleChildrenResponse data = this.removeAllMenusFromRoleChildrenUseCase.execute(roleId, authPrincipal);
        return ReadRetrieveResponse.<RoleChildrenResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

}
