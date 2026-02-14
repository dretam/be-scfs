package bank_mega.corsys.infrastructure.adapter.in.validation.common.impl;

import bank_mega.corsys.infrastructure.adapter.in.validation.common.OptionalNotBlank;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Optional;

public class OptionalNotBlankImpl implements ConstraintValidator<OptionalNotBlank, Optional<String>> {

    @Override
    public boolean isValid(Optional<String> value, ConstraintValidatorContext context) {
        return value.isEmpty() || !value.get().isBlank();
    }

}
