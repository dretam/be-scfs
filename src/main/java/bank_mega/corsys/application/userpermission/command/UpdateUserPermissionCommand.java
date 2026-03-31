package bank_mega.corsys.application.userpermission.command;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Command to update a user permission override effect.
 */
public record UpdateUserPermissionCommand(
        @NotNull(message = "userId is required")
        UUID userId,

        @NotNull(message = "permissionId is required")
        UUID permissionId,

        @NotNull(message = "effect is required")
        String effect
) {
}
