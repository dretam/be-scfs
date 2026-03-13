package bank_mega.corsys.application.user.command;

import bank_mega.corsys.infrastructure.adapter.in.validation.role.RoleIdExist;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateUserCommand(

        @NotNull
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username can only contain letters and numbers")
        String username,

        @NotNull
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character")
        String password,

        @NotNull
        @RoleIdExist
        Long roleId,

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
