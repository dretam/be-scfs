package bank_mega.corsys.application.userpermission.command;

import jakarta.validation.constraints.NotNull;

/**
 * Command to create a user permission override.
 */
public record CreateUserPermissionCommand(
        @NotNull(message = "userId is required")
        Long userId,

        @NotNull(message = "permissionId is required")
        Long permissionId,

        @NotNull(message = "effect is required")
        String effect
) {
}
