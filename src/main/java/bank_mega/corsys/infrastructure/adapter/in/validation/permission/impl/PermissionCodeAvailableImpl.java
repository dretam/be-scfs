package bank_mega.corsys.infrastructure.adapter.in.validation.permission.impl;

import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionCode;
import bank_mega.corsys.domain.repository.PermissionRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.permission.PermissionCodeAvailable;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class PermissionCodeAvailableImpl implements ConstraintValidator<PermissionCodeAvailable, String> {

    private final PermissionRepository permissionRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value) && !value.isBlank()) {
            Permission existing = permissionRepository.findFirstByCode(new PermissionCode(value)).orElse(null);
            return existing == null;
        }
        return true;
    }

}
