package bank_mega.corsys.application.rolechildren.command;

import lombok.Builder;

@Builder
public record SoftDeleteRoleChildrenCommand(
    String id,
    String parentId
) {
}
