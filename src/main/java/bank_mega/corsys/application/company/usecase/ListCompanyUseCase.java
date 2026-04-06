package bank_mega.corsys.application.company.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.company.Company;
import bank_mega.corsys.domain.repository.CompanyRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListCompanyUseCase {
    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public List<@NonNull Company> execute() {
        return companyRepository.findAllByAuditDeletedAtIsNull();
    }
}
