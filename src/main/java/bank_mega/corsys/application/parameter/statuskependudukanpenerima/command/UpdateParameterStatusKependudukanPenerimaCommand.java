package bank_mega.corsys.application.parameter.statuskependudukanpenerima.command;

import jakarta.validation.constraints.NotNull;
import java.util.Optional;

public record UpdateParameterStatusKependudukanPenerimaCommand(
        @NotNull
        Integer code,

        String value
) {
}
