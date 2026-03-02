package bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.impl;

import bank_mega.corsys.domain.model.internaluser.InternalUser;
import bank_mega.corsys.domain.model.internaluser.InternalUserName;
import bank_mega.corsys.domain.repository.InternalUserRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.internaluser.InternalUserNameExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class InternalUserNameExistImpl implements ConstraintValidator<InternalUserNameExist, String> {

    private final InternalUserRepository internalUserRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            InternalUser internalUser = internalUserRepository.findFirstByUserName(new InternalUserName(value)).orElse(null);
            return internalUser != null;
        } else {
            return true;
        }
    }

}
