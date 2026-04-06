package bank_mega.corsys.infrastructure.adapter.in.validation.user.impl;

import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.repository.UserRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserCompareExistingPassword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@RequiredArgsConstructor
public class UserCompareExistingPasswordImpl implements ConstraintValidator<UserCompareExistingPassword, Object> {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value == null) {
            return false;
        }

        BeanWrapperImpl wrapper = new BeanWrapperImpl(value);

        String oldPassword = (String) wrapper.getPropertyValue("oldPassword");
        UUID userId = (UUID) wrapper.getPropertyValue("id");

        if (oldPassword == null || userId == null) {
            return false;
        }

        User user = userRepository
                .findFirstByIdAndAuditDeletedAtIsNull(new UserId(userId))
                .orElse(null);

        if (user == null) {
            return false;
        }

        boolean isMatch = passwordEncoder.matches(
                oldPassword.trim(),
                user.getPassword().value()
        );

        if (isMatch) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        context.buildConstraintViolationWithTemplate("Old password is wrong")
                .addPropertyNode("oldPassword")
                .addConstraintViolation();

        return false;
    }
}
