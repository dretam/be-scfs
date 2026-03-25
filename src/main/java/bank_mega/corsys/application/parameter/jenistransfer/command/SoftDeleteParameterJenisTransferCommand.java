package bank_mega.corsys.application.parameter.jenistransfer.command;

import jakarta.validation.constraints.NotNull;

public record SoftDeleteParameterJenisTransferCommand(
        @NotNull
        Integer code
) {
}
