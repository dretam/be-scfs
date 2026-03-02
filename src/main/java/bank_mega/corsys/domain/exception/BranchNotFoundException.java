package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.branch.BranchId;

public class BranchNotFoundException extends DomainException {

    public BranchNotFoundException(BranchId branchId) {
        super("Branch not found with id: " + branchId.value());
    }

}
