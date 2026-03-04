package bank_mega.corsys.infrastructure.adapter.in.validation.permission.impl;

import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.repository.PermissionRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.permission.PermissionIdExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class PermissionIdExistImpl implements ConstraintValidator<PermissionIdExist, Long> {

    private final PermissionRepository permissionRepository;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            Permission permission = permissionRepository.findFirstByIdAndAuditDeletedAtIsNull(
                    new PermissionId(value)).orElse(null);
            return permission != null;
        } else {
            return true;
        }
    }

}
