package bank_mega.corsys.application.parameter.metodepembayaranbunga.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SoftDeleteParameterMetodePembayaranBungaCommand(
        @NotNull
        @NotBlank
        String code
) {
}
