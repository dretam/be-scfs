package bank_mega.corsys.application.company.usecase;

import bank_mega.corsys.application.assembler.CompanyAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.company.dto.CompanyResponse;
import bank_mega.corsys.domain.exception.CompanyNotFoundException;
import bank_mega.corsys.domain.model.company.Company;
import bank_mega.corsys.domain.model.company.CompanyId;
import bank_mega.corsys.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveCompanyUseCase {

    private CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public CompanyResponse execute(String id) {
        // 1. Validasi bahwa company ada
        Company company = companyRepository.findFirstByIdAndAuditDeletedAtIsNull(new CompanyId(id)).orElseThrow(
                () -> new CompanyNotFoundException(new CompanyId(id))
        );

        // 2. convert ke response DTO
        return CompanyAssembler.toResponse(company);
    }

}
