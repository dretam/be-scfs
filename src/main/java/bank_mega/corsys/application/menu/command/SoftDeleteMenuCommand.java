package bank_mega.corsys.application.menu.command;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SoftDeleteMenuCommand(
        UUID id
) {
}
