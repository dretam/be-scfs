package bank_mega.corsys.application.userpermission.command;

import jakarta.validation.constraints.NotNull;

/**
 * Command to update a user permission override effect.
 */
public record UpdateUserPermissionCommand(
        @NotNull(message = "userId is required")
        Long userId,

        @NotNull(message = "permissionId is required")
        Long permissionId,

        @NotNull(message = "effect is required")
        String effect
) {
}
