package bank_mega.corsys.application.parameter.jenistransaksiskn.command;

import jakarta.validation.constraints.NotNull;

public record SoftDeleteParameterJenisTransaksiSknCommand(
        @NotNull
        Integer code
) {
}
