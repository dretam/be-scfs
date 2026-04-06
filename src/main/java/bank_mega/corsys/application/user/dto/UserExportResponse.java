package bank_mega.corsys.application.user.dto;

import bank_mega.corsys.domain.model.company.CompanyCif;
import bank_mega.corsys.domain.model.role.RoleCode;
import lombok.Builder;

@Builder
public record UserExportResponse(
        String username,
        String password,
        String fullName,
        String email,
        RoleCode roleCode,
        CompanyCif companyCif
) {
}
