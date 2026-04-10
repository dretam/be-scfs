package bank_mega.corsys.infrastructure.adapter.in.validation.roleChildren.impl;

import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenCode;
import bank_mega.corsys.domain.repository.RoleChildrenRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.roleChildren.RoleChildrenIdExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class RoleChildrenIdExistImpl implements ConstraintValidator<RoleChildrenIdExist, String> {

    private final RoleChildrenRepository roleChildrenRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            RoleChildren roleChildren =
                    roleChildrenRepository.findFirstByIdAndAuditDeletedAtIsNull(new RoleChildrenCode(value)).orElse(null);
            return roleChildren != null;
        } else {
            return true;
        }
    }

}
