package bank_mega.corsys.infrastructure.adapter.in.validation.role.impl;

import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.domain.repository.RoleRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.role.RoleNameAvailable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class RoleNameAvailableImpl implements ConstraintValidator<RoleNameAvailable, String> {

    private final RoleRepository roleRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            Role role = roleRepository.findFirstByName(new RoleName(value)).orElse(null);
            return role == null;
        } else {
            return true;
        }
    }

}
