package bank_mega.corsys.application.parameter.sumberdana.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SoftDeleteParameterSumberDanaCommand(
        @NotNull
        @NotBlank
        String code
) {
}
