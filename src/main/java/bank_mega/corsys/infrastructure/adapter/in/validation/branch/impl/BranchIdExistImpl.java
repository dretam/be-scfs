package bank_mega.corsys.infrastructure.adapter.in.validation.branch.impl;

import bank_mega.corsys.domain.model.branch.Branch;
import bank_mega.corsys.domain.model.branch.BranchId;
import bank_mega.corsys.domain.repository.BranchRepository;
import bank_mega.corsys.infrastructure.adapter.in.validation.branch.BranchIdExist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@RequiredArgsConstructor
public class BranchIdExistImpl implements ConstraintValidator<BranchIdExist, String> {

    private final BranchRepository branchRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (Objects.nonNull(value) && !value.isBlank()) {
            try {
                Long id = Long.parseLong(value);
                Branch branch = branchRepository.findFirstById(new BranchId(id)).orElse(null);
                return branch != null;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            return true;
        }
    }

}
