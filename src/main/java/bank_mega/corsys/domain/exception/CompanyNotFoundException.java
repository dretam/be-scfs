package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.company.CompanyCif;
import bank_mega.corsys.domain.model.company.CompanyId;

public class CompanyNotFoundException extends DomainException {

    public CompanyNotFoundException(CompanyId companyId) {
        super("Company not found with id: " + companyId.value());
    }

    public CompanyNotFoundException(CompanyCif companyCif) {
        super("Company not found with cif: " + companyCif.value());
    }
}
