package bank_mega.corsys.application.user.dto;

import bank_mega.corsys.domain.model.company.CompanyCif;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.user.UserId;

public record SendTokenChangePassUserResponse(
        UserId userId,
        String username,
        String fullName,
        String email,
        RoleCode roleCode,
        CompanyCif companyCif
) {
}
