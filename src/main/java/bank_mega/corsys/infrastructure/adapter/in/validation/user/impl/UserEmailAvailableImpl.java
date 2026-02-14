package bank_mega.corsys.infrastructure.adapter.in.validation.user.impl;

import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserEmail;
import bank_mega.corsys.domain.repository.UserRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserEmailAvailable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class UserEmailAvailableImpl implements ConstraintValidator<UserEmailAvailable, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            User user = userRepository.findFirstByEmail(new UserEmail(value)).orElse(null);
            return user == null;
        } else {
            return true;
        }
    }

}
