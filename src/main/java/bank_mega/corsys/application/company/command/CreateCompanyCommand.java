package bank_mega.corsys.application.company.command;

import bank_mega.corsys.domain.model.company.*;
import lombok.Builder;

@Builder
public record CreateCompanyCommand(
        String cif,
        String name,
        CompanyType companyType,
        String companyRmUserId,
        Integer companyDiscountRate,
        Integer companyMaxFinancing
) {
}
