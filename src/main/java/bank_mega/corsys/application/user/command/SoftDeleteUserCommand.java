package bank_mega.corsys.application.user.command;

import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserIdExist;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record SoftDeleteUserCommand(

        @NotNull
        @UserIdExist
        UUID id
) {
}
