package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.company.dto.CompanyResponse;
import bank_mega.corsys.domain.model.company.Company;

public class CompanyAssembler {
    public static CompanyResponse toResponse(Company saved) {
        if (saved == null) return null;
        return CompanyResponse.builder()
                .companyCif(saved.getCompanyCif().value())
                .companyName(saved.getCompanyName().value())
                .companyType(saved.getCompanyType().name())
                .companyRmUserId(saved.getCompanyRmUserId().value())
                .createdAt(saved.getAudit().createdAt())
                .createdBy(saved.getAudit().createdBy())
                .updatedAt(saved.getAudit().updatedAt())
                .updatedBy(saved.getAudit().updatedBy())
                .deletedAt(saved.getAudit().deletedAt())
                .deletedBy(saved.getAudit().deletedBy())
                .build();
    }
}
