package bank_mega.corsys.application.company.command;

import bank_mega.corsys.domain.model.company.CompanyType;
import lombok.Builder;

import java.util.Optional;
import java.util.UUID;

@Builder
public record UpdateCompanyCommand(
        UUID companyId,
        Optional<String> companyCif,
        Optional<String> companyName,
        Optional<CompanyType> companyType,
        Optional<UUID> companyRmUserId,
        Optional<Integer> companyDiscountRate,
        Optional<Integer> companyMaxFinancing
) {
}
