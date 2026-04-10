package bank_mega.corsys.application.rolechildren.command;

import lombok.Builder;

import java.util.Optional;

@Builder
public record UpdateRoleChildrenCommand(
        String id,
        String parentId,
        Optional<String> name,
        Optional<String> icon,
        Optional<String> description
) {
}
