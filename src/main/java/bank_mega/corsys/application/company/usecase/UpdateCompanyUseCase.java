package bank_mega.corsys.application.company.usecase;

import bank_mega.corsys.application.assembler.CompanyAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.company.command.UpdateCompanyCommand;
import bank_mega.corsys.application.company.dto.CompanyResponse;
import bank_mega.corsys.domain.exception.CompanyNotFoundException;
import bank_mega.corsys.domain.model.company.*;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class UpdateCompanyUseCase {

    private CompanyRepository companyRepository;

    @Transactional
    public CompanyResponse execute(UpdateCompanyCommand command, User authPrincipal) {

        // 1. Validasi bahwa user ada
        Company company =
                companyRepository.findFirstByIdAndAuditDeletedAtIsNull(new CompanyId(command.companyId())).orElseThrow(
                () -> new CompanyNotFoundException(new CompanyId(command.companyId()))
        );

        // 2. Transaksi
        command.companyCif().ifPresent(cif -> company.changeCompanyCif(new CompanyCif(cif)));

        command.companyName().ifPresent(name -> company.changeCompanyName(new CompanyName(name)));

        command.companyType().ifPresent(type -> company.changeCompanyType(type));

        command.companyRmUserId().ifPresent(rmUserId -> company.changeCompanyRmUserId(new CompanyRmUserId(rmUserId)));

        command.companyDiscountRate().ifPresent(discountRate -> company.changeCompanyDiscountRate(new CompanyDiscountRate(discountRate)));

        command.companyMaxFinancing().ifPresent(maxFinancing -> company.changeCompanyMaxFinancing(new CompanyMaxFinancing(maxFinancing)));

        company.updateAudit(authPrincipal.getId().value());

        // 3. simpan di repository (masuk ke infra → mapper → jpa)
        Company saved = companyRepository.save(company);

        // 4. convert ke response DTO
        return CompanyAssembler.toResponse(saved);
    }

}
