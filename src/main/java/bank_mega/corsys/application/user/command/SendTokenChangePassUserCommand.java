package bank_mega.corsys.application.user.command;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record SendTokenChangePassUserCommand(
        @NotNull
        @Email(message = "Invalid email format")
        String email
) {
}
