package bank_mega.corsys.application.role.command;

import lombok.Builder;

@Builder
public record CreateRoleCommand(
        String name,
        String icon,
        String description
) {
}
