package bank_mega.corsys.infrastructure.adapter.in.validation.user.impl;

import bank_mega.corsys.application.user.command.UpdateUserCommand;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserEmail;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.repository.UserRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UpdateUserCommandEmailAvailable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class UpdateUserCommandEmailAvailableImpl implements ConstraintValidator<UpdateUserCommandEmailAvailable, UpdateUserCommand> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(UpdateUserCommand command, ConstraintValidatorContext context) {
        if (command.email().isEmpty()) {
            return true;
        }

        if (command.id().describeConstable().isPresent()) {
            User user = userRepository.findFirstByEmailAndIdNot(
                    new UserEmail(command.email().get()),
                    new UserId(command.id())
            ).orElse(null);
            return user == null;
        } else {
            return true;
        }
    }

}
