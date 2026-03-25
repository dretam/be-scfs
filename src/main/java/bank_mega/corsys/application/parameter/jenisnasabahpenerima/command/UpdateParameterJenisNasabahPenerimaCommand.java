package bank_mega.corsys.application.parameter.jenisnasabahpenerima.command;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;

public record UpdateParameterJenisNasabahPenerimaCommand(
        @NotNull
        Integer code,

        String value
) {
}
