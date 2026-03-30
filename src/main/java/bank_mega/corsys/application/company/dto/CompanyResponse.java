package bank_mega.corsys.application.company.dto;

import bank_mega.corsys.domain.model.company.CompanyType;
import lombok.Builder;

import java.time.Instant;

@Builder
public record CompanyResponse(
        String companyId,
        String companyCif,
        String companyName,
        String companyType,
        String companyRmUserId,
        Integer companyDiscountRate,
        Integer companyMaxFinancing,
        Instant createdAt,
        String createdBy,
        Instant updatedAt,
        String updatedBy,
        Instant deletedAt,
        String deletedBy
) {
}
