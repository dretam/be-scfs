package bank_mega.corsys.application.user.command;

import bank_mega.corsys.infrastructure.adapter.in.validation.common.OptionalNotBlank;
import bank_mega.corsys.infrastructure.adapter.in.validation.role.RoleIdExist;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UpdateUserCommandEmailAvailable;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UpdateUserCommandNameAvailable;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserIdExist;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
@UpdateUserCommandNameAvailable
@UpdateUserCommandEmailAvailable
public record UpdateUserCommand(

        @NotNull
        @UserIdExist
        Long id,

        @OptionalNotBlank
        @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
        Optional<String> name,

        @OptionalNotBlank
        Optional<String> email,

        @OptionalNotBlank
        Optional<String> existingPassword,

        @OptionalNotBlank
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character"
        )
        Optional<String> password,

        @RoleIdExist
        Optional<Long> roleId,

        @Nullable
        List<PermissionOverride> permissionOverrides
) {

        public record PermissionOverride(

                @NotNull
                Long permissionId,

                @NotNull
                PermissionEffect effect
        ) {
        }

        public enum PermissionEffect {
                ALLOW,
                DENY
        }
}