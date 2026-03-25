package bank_mega.corsys.application.parameter.automatictransfer.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SoftDeleteParameterAutomaticTransferCommand(
        @NotNull
        @NotBlank
        String code
) {
}
