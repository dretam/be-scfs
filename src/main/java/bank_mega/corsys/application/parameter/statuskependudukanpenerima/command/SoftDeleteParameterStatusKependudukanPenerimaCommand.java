package bank_mega.corsys.application.parameter.statuskependudukanpenerima.command;

import jakarta.validation.constraints.NotNull;

public record SoftDeleteParameterStatusKependudukanPenerimaCommand(
        @NotNull
        Integer code
) {
}
