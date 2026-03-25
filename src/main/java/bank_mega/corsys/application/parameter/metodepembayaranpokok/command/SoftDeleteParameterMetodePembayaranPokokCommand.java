package bank_mega.corsys.application.parameter.metodepembayaranpokok.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SoftDeleteParameterMetodePembayaranPokokCommand(
        @NotNull
        @NotBlank
        String code
) {
}
