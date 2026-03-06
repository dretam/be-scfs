package bank_mega.corsys.infrastructure.adapter.in.api.v1;

import bank_mega.corsys.application.common.dto.ReadListResponse;
import bank_mega.corsys.application.common.dto.ReadRetrieveResponse;
import bank_mega.corsys.application.userpermission.command.CreateUserPermissionCommand;
import bank_mega.corsys.application.userpermission.command.DeleteUserPermissionCommand;
import bank_mega.corsys.application.userpermission.command.UpdateUserPermissionCommand;
import bank_mega.corsys.application.userpermission.dto.UserPermissionResponse;
import bank_mega.corsys.application.userpermission.usecase.CreateUserPermissionUseCase;
import bank_mega.corsys.application.userpermission.usecase.DeleteUserPermissionUseCase;
import bank_mega.corsys.application.userpermission.usecase.RetrieveUserPermissionsUseCase;
import bank_mega.corsys.application.userpermission.usecase.UpdateUserPermissionUseCase;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.infrastructure.config.security.HasPermission;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Tag(name = "User Permissions")
@RestController
@RequestMapping("/api/v1/users/{userId}/permissions")
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authorization")
@RateLimiter(name = "global")
public class UserPermissionApi {

    private final CreateUserPermissionUseCase createUserPermissionUseCase;
    private final DeleteUserPermissionUseCase deleteUserPermissionUseCase;
    private final RetrieveUserPermissionsUseCase retrieveUserPermissionsUseCase;
    private final UpdateUserPermissionUseCase updateUserPermissionUseCase;

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("USER_READ")
    public ReadListResponse<List<UserPermissionResponse>> list(
            @PathVariable Long userId
    ) {
        List<UserPermissionResponse> data = retrieveUserPermissionsUseCase.execute(userId);
        return ReadListResponse.<List<UserPermissionResponse>>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @GetMapping(
            path = "/effective",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("USER_READ")
    public ReadRetrieveResponse<List<String>> getEffectivePermissions(
            @PathVariable Long userId
    ) {
        // For effective permissions, we need to load the user with role
        // This endpoint assumes the user exists and has a role
        List<String> effectivePermissions = retrieveUserPermissionsUseCase.executeEffectivePermissions(userId);
        return ReadRetrieveResponse.<List<String>>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(effectivePermissions)
                .build();
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("USER_UPDATE")
    public ReadRetrieveResponse<UserPermissionResponse> create(
            @PathVariable Long userId,
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid CreateUserPermissionCommand command
    ) {
        // Ensure userId in path matches command
        CreateUserPermissionCommand validatedCommand = new CreateUserPermissionCommand(
                userId,
                command.permissionId(),
                command.effect()
        );
        UserPermissionResponse data = createUserPermissionUseCase.execute(validatedCommand, authPrincipal);
        return ReadRetrieveResponse.<UserPermissionResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @PutMapping(
            path = "/{permissionId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("USER_UPDATE")
    public ReadRetrieveResponse<UserPermissionResponse> update(
            @PathVariable Long userId,
            @PathVariable Long permissionId,
            @AuthenticationPrincipal User authPrincipal,
            @RequestBody @Valid UpdateUserPermissionCommand command
    ) {
        // Ensure userId and permissionId in path match command
        UpdateUserPermissionCommand validatedCommand = new UpdateUserPermissionCommand(
                userId,
                permissionId,
                command.effect()
        );
        UserPermissionResponse data = updateUserPermissionUseCase.execute(validatedCommand, authPrincipal);
        return ReadRetrieveResponse.<UserPermissionResponse>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(data)
                .build();
    }

    @DeleteMapping(
            path = "/{permissionId}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @HasPermission("USER_UPDATE")
    public ReadRetrieveResponse<Void> delete(
            @PathVariable Long userId,
            @PathVariable Long permissionId,
            @AuthenticationPrincipal User authPrincipal
    ) {
        DeleteUserPermissionCommand command = new DeleteUserPermissionCommand(userId, permissionId);
        deleteUserPermissionUseCase.execute(command, authPrincipal);
        return ReadRetrieveResponse.<Void>builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(null)
                .build();
    }

}
