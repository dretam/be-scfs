package bank_mega.corsys.application.company.command;

import bank_mega.corsys.domain.model.company.*;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CreateCompanyCommand(
        String cif,
        String name,
        CompanyType companyType,
        UUID companyRmUserId,
        Integer companyDiscountRate,
        Integer companyMaxFinancing
) {
}
