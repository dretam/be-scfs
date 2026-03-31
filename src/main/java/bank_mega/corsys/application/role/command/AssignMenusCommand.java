package bank_mega.corsys.application.role.command;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record AssignMenusCommand(
        @NotNull String roleId,
        @NotEmpty Set<UUID> menuIds
) {
}
