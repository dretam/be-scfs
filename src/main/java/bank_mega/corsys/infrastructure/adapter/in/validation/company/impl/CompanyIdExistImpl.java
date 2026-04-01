package bank_mega.corsys.infrastructure.adapter.in.validation.company.impl;

import bank_mega.corsys.domain.model.company.Company;
import bank_mega.corsys.domain.model.company.CompanyId;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.repository.CompanyRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.company.CompanyIdExist;
import bank_mega.corsys.infrastructure.adapter.in.validation.role.RoleIdExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class CompanyIdExistImpl implements ConstraintValidator<CompanyIdExist, UUID> {
    private final CompanyRepository companyRepository;

    @Override
    public boolean isValid(UUID value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value)) {
            Company company = companyRepository.findFirstByIdAndAuditDeletedAtIsNull(new CompanyId(value)).orElse(null);
            return company != null;
        } else {
            return true;
        }
    }
}
