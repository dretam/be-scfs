package bank_mega.corsys.application.rolechildren.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record AssignPermissionsCommand(
        @NotNull String roleId,
        @NotNull String roleParentId,
        @NotEmpty Set<UUID> permissionIds
) {
}
