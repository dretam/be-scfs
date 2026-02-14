package bank_mega.corsys.infrastructure.adapter.in.validation.user.impl;

import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserEmail;
import bank_mega.corsys.domain.repository.UserRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserEmailAvailable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserEmailOptionalAvailableImpl implements ConstraintValidator<UserEmailAvailable, Optional<String>> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(Optional<String> value, ConstraintValidatorContext context) {
        if (value.isEmpty()) {
            return true;
        }

        User user = userRepository.findFirstByEmail(new UserEmail(value.get())).orElse(null);
        return user == null;
    }

}
