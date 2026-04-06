package bank_mega.corsys.infrastructure.adapter.in.validation.user.impl;

import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserPasswordConfirmation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Objects;


@RequiredArgsConstructor
public class UserPasswordConfirmationImpl implements ConstraintValidator<UserPasswordConfirmation, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String password = Objects.requireNonNull(new BeanWrapperImpl(value).getPropertyValue("password")).toString();
        String passwordConfirmation = Objects.requireNonNull(new BeanWrapperImpl(value).getPropertyValue("passwordConfirmation")).toString();

        if(password.trim().equals(passwordConfirmation.trim())) {
            return true;
        }

        context.disableDefaultConstraintViolation();

        context.buildConstraintViolationWithTemplate("Password and password confirmation not match")
                .addPropertyNode("passwordConfirmation")
                .addConstraintViolation();

        return false;
    }
}
