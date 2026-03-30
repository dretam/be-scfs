package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.company.Company;
import bank_mega.corsys.domain.model.company.CompanyCif;
import bank_mega.corsys.domain.model.company.CompanyId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface CompanyRepository {
    Company save(Company company);

    void delete(Company company);

    long count();

    Page<@NonNull Company> findAllPageable(int page, int size, String sort, String filter);

    Optional<Company> findFirstById(CompanyId companyId);

    Optional<Company> findFirstByIdAndAuditDeletedAtIsNull(CompanyId companyId);

    Optional<Company> findFirstByCif(CompanyCif companyCif);
}
