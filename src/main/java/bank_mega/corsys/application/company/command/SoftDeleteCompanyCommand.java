package bank_mega.corsys.application.company.command;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SoftDeleteCompanyCommand(
        UUID companyId
) {
}
