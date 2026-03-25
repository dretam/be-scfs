package bank_mega.corsys.application.parameter.jenistransaksirtgs.command;

import jakarta.validation.constraints.NotNull;

public record CreateParameterJenisTransaksiRtgsCommand(
        @NotNull
        Integer code,

        @NotNull
        String value
) {
}
