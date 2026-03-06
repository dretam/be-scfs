package bank_mega.corsys.application.userpermission.command;

import jakarta.validation.constraints.NotNull;

/**
 * Command to delete a user permission override.
 */
public record DeleteUserPermissionCommand(
        @NotNull(message = "userId is required")
        Long userId,

        @NotNull(message = "permissionId is required")
        Long permissionId
) {
}
