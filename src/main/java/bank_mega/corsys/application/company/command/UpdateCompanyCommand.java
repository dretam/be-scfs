package bank_mega.corsys.application.company.command;

import bank_mega.corsys.domain.model.company.CompanyType;
import lombok.Builder;

import java.util.Optional;

@Builder
public record UpdateCompanyCommand(
        String companyId,
        Optional<String> companyCif,
        Optional<String> companyName,
        Optional<CompanyType> companyType,
        Optional<String> companyRmUserId,
        Optional<Integer> companyDiscountRate,
        Optional<Integer> companyMaxFinancing
) {
}
