package bank_mega.corsys.application.company.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.CompanyNotFoundException;
import bank_mega.corsys.domain.model.company.Company;
import bank_mega.corsys.domain.model.company.CompanyId;
import bank_mega.corsys.domain.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class DeleteCompanyUseCase {

    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyId execute(UUID id) {
        // 1. Validasi bahwa company ada
        Company company = companyRepository.findFirstById(new CompanyId(id)).orElseThrow(
                () -> new CompanyNotFoundException(new CompanyId(id))
        );

        // 2. Simpan di repository (masuk ke infra → mapper → jpa)
        companyRepository.delete(company);

        // 3. convert ke response DTO
        return new CompanyId(id);
    }
}
