package bank_mega.corsys.application.company.usecase;

import bank_mega.corsys.application.assembler.CompanyAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.company.command.CreateCompanyCommand;
import bank_mega.corsys.application.company.dto.CompanyResponse;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.company.*;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CreateCompanyUseCase {

    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyResponse execute(CreateCompanyCommand command, User authPrincipal) {
        // 1. set domain company
        Company newCompany = new Company(
                null,
                new CompanyCif(command.cif()),
                new CompanyName(command.name()),
                command.companyType(),
                new CompanyRmUserId(command.companyRmUserId()),
                new CompanyDiscountRate(command.companyDiscountRate()),
                new CompanyMaxFinancing(command.companyMaxFinancing()),
                AuditTrail.create(authPrincipal.getId().value())
        );

        // 2. simpan di repository (masuk ke infra → mapper → jpa)
        Company saved = companyRepository.save(newCompany);

        // 3. convert ke response DTO
        return CompanyAssembler.toResponse(saved);
    }

}
