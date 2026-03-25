package bank_mega.corsys.application.parameter.statuskependudukanpenerima.command;

import jakarta.validation.constraints.NotNull;

public record CreateParameterStatusKependudukanPenerimaCommand(
        @NotNull
        Integer code,

        @NotNull
        String value
) {
}
