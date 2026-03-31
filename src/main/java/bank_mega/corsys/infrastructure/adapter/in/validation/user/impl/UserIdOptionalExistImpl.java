package bank_mega.corsys.infrastructure.adapter.in.validation.user.impl;

import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserId;
import bank_mega.corsys.domain.repository.UserRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.user.UserIdExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UserIdOptionalExistImpl implements ConstraintValidator<UserIdExist, Optional<UUID>> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(Optional<UUID> value, ConstraintValidatorContext context) {
        User user = userRepository.findFirstByIdAndAuditDeletedAtIsNull(new UserId(value.get())).orElse(null);
        return user != null;
    }

}
