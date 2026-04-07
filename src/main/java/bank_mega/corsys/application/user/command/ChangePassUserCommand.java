package bank_mega.corsys.application.user.command;

import bank_mega.corsys.infrastructure.adapter.in.validation.user.ForgotPasswordTokenHashExist;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserCompareExistingPassword;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserIdExist;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserPasswordConfirmation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@UserPasswordConfirmation
@UserCompareExistingPassword
public record ChangePassUserCommand(
        @NotNull
        @UserIdExist
        UUID id,

        @NotNull
        @ForgotPasswordTokenHashExist
        String forgotPasswordTokenHash,

        @NotNull
        String username,

        @NotNull
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character")
        String oldPassword,

        @NotNull
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character")
        String password,

        @NotNull
        @Size(min = 8, max = 100, message = "Password confirmation must be between 8 and 100 characters")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Password confirmation must contain at least one digit, one lowercase, one uppercase, and one special " +
                        "character")
        String passwordConfirmation
) {
}
