package bank_mega.corsys.application.parameter.jenisnasabahpenerima.command;

import jakarta.validation.constraints.NotNull;

public record CreateParameterJenisNasabahPenerimaCommand(
        @NotNull
        Integer code,

        @NotNull
        String value
) {
}
