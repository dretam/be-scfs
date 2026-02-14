package bank_mega.corsys.application.user.command;

import bank_mega.corsys.infrastructure.adapter.in.validation.role.RoleIdExist;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserEmailAvailable;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserNameAvailable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateUserCommand(

        @NotNull
        @UserNameAvailable
        String name,

        @NotNull
        @Email
        @UserEmailAvailable
        String email,

        @NotNull
        String password,

        @NotNull
        @RoleIdExist
        Long roleId
) {
}
