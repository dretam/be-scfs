package bank_mega.corsys.domain.model.company;

import bank_mega.corsys.domain.model.common.AuditTrail;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class Company {
    private final CompanyId companyId;
    private CompanyCif companyCif;
    private CompanyName companyName;
    private CompanyType companyType;
    private CompanyRmUserId companyRmUserId;
    private CompanyDiscountRate companyDiscountRate;
    private CompanyMaxFinancing companyMaxFinancing;
    private AuditTrail audit;

    public Company(
            CompanyId companyId,
            CompanyCif companyCif,
            CompanyName companyName,
            CompanyType companyType,
            CompanyRmUserId companyRmUserId,
            CompanyDiscountRate companyDiscountRate,
            CompanyMaxFinancing companyMaxFinancing,
            AuditTrail audit
    ) {
        this.companyId = companyId;
        this.companyCif = companyCif;
        this.companyName = companyName;
        this.companyType = companyType;
        this.companyRmUserId = companyRmUserId;
        this.companyDiscountRate = companyDiscountRate;
        this.companyMaxFinancing = companyMaxFinancing;
        this.audit = audit;
    }

    public void changeCompanyCif(CompanyCif newCompanyCif) {
        if (newCompanyCif != null) {
            this.companyCif = newCompanyCif;
        }
    }

    public void changeCompanyName(CompanyName newCompanyName) {
        if (newCompanyName != null) {
            this.companyName = newCompanyName;
        }
    }

    public void changeCompanyRmUserId(CompanyRmUserId newCompanyRmUserId) {
        if (newCompanyRmUserId != null) {
            this.companyRmUserId = newCompanyRmUserId;
        }
    }

    public void changeCompanyType(CompanyType newCompanyType) {
        if (newCompanyType != null) {
            this.companyType = newCompanyType;
        }
    }

    public void changeCompanyDiscountRate(CompanyDiscountRate newCompanyDiscountRate) {
        if (newCompanyDiscountRate != null) {
            this.companyDiscountRate = newCompanyDiscountRate;
        }
    }

    public void changeCompanyMaxFinancing(CompanyMaxFinancing newCompanyMaxFinancing) {
        if (newCompanyMaxFinancing != null) {
            this.companyMaxFinancing = newCompanyMaxFinancing;
        }
    }

    public void updateAudit(UUID updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(UUID deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
