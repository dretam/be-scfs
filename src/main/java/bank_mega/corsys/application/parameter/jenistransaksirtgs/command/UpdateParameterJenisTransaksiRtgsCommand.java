package bank_mega.corsys.application.parameter.jenistransaksirtgs.command;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;

public record UpdateParameterJenisTransaksiRtgsCommand(
        @NotNull
        Integer code,

        String value
) {
}
