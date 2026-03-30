package bank_mega.corsys.application.company.usecase;

import bank_mega.corsys.application.assembler.CompanyAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.company.command.SoftDeleteCompanyCommand;
import bank_mega.corsys.application.company.dto.CompanyResponse;
import bank_mega.corsys.domain.exception.CompanyNotFoundException;
import bank_mega.corsys.domain.model.company.Company;
import bank_mega.corsys.domain.model.company.CompanyId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteCompanyUseCase {

    private CompanyRepository companyRepository;

    @Transactional
    public CompanyResponse execute(SoftDeleteCompanyCommand command, User authPrincipal) {

        // 1. Validasi bahwa company ada
        Company company =
                companyRepository.findFirstByIdAndAuditDeletedAtIsNull(new CompanyId(command.companyId())).orElseThrow(
                () -> new CompanyNotFoundException(new CompanyId(command.companyId()))
        );

        // 2. Update Soft Delete
        company.deleteAudit(authPrincipal.getId().value());

        // 3. Simpan di repository (masuk ke infra → mapper → jpa)
        Company saved = companyRepository.save(company);

        // 4. convert ke response DTO
        return CompanyAssembler.toResponse(saved);
    }

}
