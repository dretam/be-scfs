package bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.impl;

import bank_mega.corsys.domain.model.internaluser.InternalUser;
import bank_mega.corsys.domain.model.internaluser.InternalUserEmail;
import bank_mega.corsys.domain.repository.InternalUserRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.InternalUserEmailAvailable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class InternalUserEmailAvailableImpl implements ConstraintValidator<InternalUserEmailAvailable, String> {

    private final InternalUserRepository internalUserRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            InternalUser internalUser = internalUserRepository.findFirstByEmail(new InternalUserEmail(value)).orElse(null);
            return internalUser == null;
        } else {
            return true;
        }
    }

}
