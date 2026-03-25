package bank_mega.corsys.application.parameter.jenisperpanjangan.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SoftDeleteParameterJenisPerpanjanganCommand(
        @NotNull
        @NotBlank
        String code
) {
}
