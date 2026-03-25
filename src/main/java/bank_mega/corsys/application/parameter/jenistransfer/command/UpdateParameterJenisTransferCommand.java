package bank_mega.corsys.application.parameter.jenistransfer.command;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;

public record UpdateParameterJenisTransferCommand(
        @NotNull
        Integer code,

        String value
) {
}
