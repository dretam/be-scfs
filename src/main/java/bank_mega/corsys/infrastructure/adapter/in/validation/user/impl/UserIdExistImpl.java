package bank_mega.corsys.infrastructure.adapter.in.validation.user.impl;

import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.repository.UserRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserIdExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class UserIdExistImpl implements ConstraintValidator<UserIdExist, UUID> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            User user = userRepository.findFirstByIdAndAuditDeletedAtIsNull(new UserId(value)).orElse(null);
            return user != null;
        } else {
            return true;
        }
    }

}
