package bank_mega.corsys.application.user.command;

import bank_mega.corsys.infrastructure.adapter.in.validation.role.RoleIdExist;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserIdExist;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.List;
import java.util.Optional;

@Builder
public record UpdateUserCommand(

        @NotNull
        @UserIdExist
        Long id,


        @Nullable
        @Pattern(
                regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$",
                message = "Password must contain at least one digit, one lowercase, one uppercase, and one special character"
        )
        String password,

        @RoleIdExist
        Optional<String> roleId,

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