package bank_mega.corsys.infrastructure.adapter.in.validation.user.impl;

import bank_mega.corsys.application.user.command.UpdateUserCommand;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.model.user.UserName;
import bank_mega.corsys.domain.repository.UserRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UpdateUserCommandNameAvailable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UpdateUserCommandNameAvailableImpl implements ConstraintValidator<UpdateUserCommandNameAvailable, UpdateUserCommand> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(UpdateUserCommand command, ConstraintValidatorContext context) {
        if (command.name().isEmpty()) {
            return true;
        }

        if (command.id().describeConstable().isPresent()) {
            User user = userRepository.findFirstByNameAndIdNot(
                    new UserName(command.name().get()),
                    new UserId(command.id())
            ).orElse(null);
            return user == null;
        } else {
            return true;
        }
    }

}
