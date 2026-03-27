package bank_mega.corsys.application.role.command;

import lombok.Builder;

@Builder
public record SoftDeleteRoleCommand(
        String id
) {
}
