package bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.impl;

import bank_mega.corsys.domain.model.internaluser.InternalUser;
import bank_mega.corsys.domain.model.internaluser.InternalUserName;
import bank_mega.corsys.domain.repository.InternalUserRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.InternalUserNameAvailable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class InternalUserNameOptionalAvailableImpl implements ConstraintValidator<InternalUserNameAvailable, Optional<String>> {

    private final InternalUserRepository internalUserRepository;

    @Override
    public boolean isValid(Optional<String> value, ConstraintValidatorContext context) {
        if (value.isEmpty()) {
            return true;
        }
        InternalUser internalUser = internalUserRepository.findFirstByUserName(new InternalUserName(value.get())).orElse(null);
        return internalUser == null;
    }

}
