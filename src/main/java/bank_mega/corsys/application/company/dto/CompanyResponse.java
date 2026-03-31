package bank_mega.corsys.application.company.dto;

import bank_mega.corsys.domain.model.company.CompanyType;
import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record CompanyResponse(
        UUID companyId,
        String companyCif,
        String companyName,
        String companyType,
        UUID companyRmUserId,
        Integer companyDiscountRate,
        Integer companyMaxFinancing,
        Instant createdAt,
        UUID createdBy,
        Instant updatedAt,
        UUID updatedBy,
        Instant deletedAt,
        UUID deletedBy
) {
}
