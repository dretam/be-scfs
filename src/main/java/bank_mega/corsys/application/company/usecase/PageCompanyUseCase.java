package bank_mega.corsys.application.company.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.company.Company;
import bank_mega.corsys.domain.repository.CompanyRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class PageCompanyUseCase {

    private final CompanyRepository companyRepository;

    @Transactional(readOnly = true)
    public Page<@NonNull Company> execute(int page, int size, String sort, String filter) {
        return companyRepository.findAllPageable(page, size, sort, filter);
    }

}
