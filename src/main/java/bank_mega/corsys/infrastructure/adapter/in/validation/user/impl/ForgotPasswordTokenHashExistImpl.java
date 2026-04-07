package bank_mega.corsys.infrastructure.adapter.in.validation.user.impl;

import bank_mega.corsys.domain.model.forgotpasstoken.ForgotToken;
import bank_mega.corsys.domain.model.forgotpasstoken.ForgotTokenHash;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.ForgotTokenRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.ForgotPasswordTokenHashExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class ForgotPasswordTokenHashExistImpl implements ConstraintValidator<ForgotPasswordTokenHashExist, String> {
    private final ForgotTokenRepository forgotTokenRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            ForgotToken forgotToken = forgotTokenRepository.findFirstValidByForgotTokenHash(new ForgotTokenHash(value)).orElse(null);
            return forgotToken != null;
        } else {
            return true;
        }
    }
}
