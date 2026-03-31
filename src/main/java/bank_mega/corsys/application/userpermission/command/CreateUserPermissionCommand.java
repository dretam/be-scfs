package bank_mega.corsys.application.userpermission.command;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

/**
 * Command to create a user permission override.
 */
public record CreateUserPermissionCommand(
        @NotNull(message = "userId is required")
        UUID userId,

        @NotNull(message = "permissionId is required")
        UUID permissionId,

        @NotNull(message = "effect is required")
        String effect
) {
}
