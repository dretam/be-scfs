package bank_mega.corsys.application.user.command;

import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserIdExist;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SoftDeleteUserCommand(

        @NotNull
        @UserIdExist
        String id
) {
}
