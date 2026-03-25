package bank_mega.corsys.application.parameter.jenistransaksiskn.command;

import jakarta.validation.constraints.NotNull;

public record CreateParameterJenisTransaksiSknCommand(
        @NotNull
        Integer code,

        @NotNull
        String value
) {
}
