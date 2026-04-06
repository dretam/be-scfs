package bank_mega.corsys.application.user.command;

import bank_mega.corsys.infrastructure.adapter.in.validation.company.CompanyIdExist;
import bank_mega.corsys.infrastructure.adapter.in.validation.role.RoleIdExist;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

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
        @Size(min = 3, max = 255, message = "Full name must be between 3 and 255 characters")
        String fullName,

        @NotNull
        @Size(min = 3, max = 255, message = "Email must be between 3 and 255 characters")
        @Email(message = "Invalid email format")
        String email,

        @NotNull
        Boolean isActive,

        @Nullable
        @Size(min = 3, max = 255, message = "Photo path must be between 3 and 255 characters")
        @Pattern(regexp = "^[a-zA-Z0-9/._-]+$", message = "Photo path can only contain letters and numbers")
        String photoPath,

        @NotNull
        @RoleIdExist
        String roleId,

        @NotNull
        @CompanyIdExist
        UUID companyId,

        @Nullable
        List<PermissionOverride> permissionOverrides
) {
        public record PermissionOverride(
                @NotNull
                UUID permissionId,

                @NotNull
                PermissionEffect effect
        ) {
        }

        public enum PermissionEffect {
                ALLOW,
                DENY
        }
}
