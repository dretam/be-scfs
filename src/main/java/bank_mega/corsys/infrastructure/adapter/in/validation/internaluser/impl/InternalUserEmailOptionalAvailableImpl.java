package bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.impl;

import bank_mega.corsys.domain.model.internaluser.InternalUser;
import bank_mega.corsys.domain.model.internaluser.InternalUserEmail;
import bank_mega.corsys.domain.repository.InternalUserRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.InternalUserEmailAvailable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class InternalUserEmailOptionalAvailableImpl implements ConstraintValidator<InternalUserEmailAvailable, Optional<String>> {

    private final InternalUserRepository internalUserRepository;

    @Override
    public boolean isValid(Optional<String> value, ConstraintValidatorContext context) {
        if (value.isEmpty()) {
            return true;
        }
        InternalUser internalUser = internalUserRepository.findFirstByEmail(new InternalUserEmail(value.get())).orElse(null);
        return internalUser == null;
    }

}
