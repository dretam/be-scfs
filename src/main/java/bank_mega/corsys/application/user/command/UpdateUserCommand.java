package bank_mega.corsys.application.user.command;

import bank_mega.corsys.infrastructure.adapter.in.validation.common.OptionalNotBlank;
import bank_mega.corsys.infrastructure.adapter.in.validation.role.RoleIdExist;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.Optional;

@Builder
@UpdateUserCommandNameAvailable
@UpdateUserCommandEmailAvailable
public record UpdateUserCommand(
        @NotNull
        @UserIdExist
        Long id,

        @OptionalNotBlank
        Optional<String> name,

        @OptionalNotBlank
        Optional<String> email,

        @OptionalNotBlank
        Optional<String> existingPassword,

        @OptionalNotBlank
        Optional<String> password,

        @RoleIdExist
        Optional<Long> roleId
) {
}
