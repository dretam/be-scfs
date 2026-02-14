package bank_mega.corsys.infrastructure.adapter.in.validation.role.impl;

import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.repository.RoleRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.role.RoleIdExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RoleIdOptionalExistImpl implements ConstraintValidator<RoleIdExist, Optional<Long>> {

    private final RoleRepository roleRepository;

    @Override
    public boolean isValid(Optional<Long> value, ConstraintValidatorContext context) {
        if (value.isEmpty()) {
            return true;
        }

        Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleId(value.get())).orElse(null);
        return role != null;
    }

}
