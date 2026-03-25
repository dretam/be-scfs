package bank_mega.corsys.application.parameter.metodepembayaranbunga.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Optional;

public record UpdateParameterMetodePembayaranBungaCommand(
        @NotNull
        @NotBlank
        @Size(min = 1, max = 1, message = "Code must be exactly 1 character")
        String code,

        @Size(max = 50, message = "Value must be between 1 and 50 characters")
        String value
) {
}
