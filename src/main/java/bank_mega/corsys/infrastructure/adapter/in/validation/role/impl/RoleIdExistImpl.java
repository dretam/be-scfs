package bank_mega.corsys.infrastructure.adapter.in.validation.role.impl;

import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.repository.RoleRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.role.RoleIdExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class RoleIdExistImpl implements ConstraintValidator<RoleIdExist, Long> {

    private final RoleRepository roleRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            Role role = roleRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleId(value)).orElse(null);
            return role != null;
        } else {
            return true;
        }
    }

}
