package bank_mega.corsys.application.parameter.jenistransaksiskn.command;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;

public record UpdateParameterJenisTransaksiSknCommand(
        @NotNull
        Integer code,

        String value
) {
}
