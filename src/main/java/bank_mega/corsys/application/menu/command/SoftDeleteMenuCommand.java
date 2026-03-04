package bank_mega.corsys.application.menu.command;

import lombok.Builder;

@Builder
public record SoftDeleteMenuCommand(
        Long id
) {
}
