package bank_mega.corsys.application.parameter.jenistransfer.command;

import jakarta.validation.constraints.NotNull;

public record CreateParameterJenisTransferCommand(
        @NotNull
        Integer code,

        @NotNull
        String value
) {
}
