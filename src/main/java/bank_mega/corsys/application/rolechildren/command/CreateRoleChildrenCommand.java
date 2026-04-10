package bank_mega.corsys.application.rolechildren.command;

import lombok.Builder;

@Builder
public record CreateRoleChildrenCommand(
        String code,
        String parentCode,
        String name,
        String icon,
        String description
) {
}
