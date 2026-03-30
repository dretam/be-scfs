package bank_mega.corsys.application.company.command;

import lombok.Builder;

@Builder
public record SoftDeleteCompanyCommand(
        String companyId
) {
}
