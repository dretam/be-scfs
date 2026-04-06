package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.company.Company;
import bank_mega.corsys.domain.model.company.CompanyCif;
import bank_mega.corsys.domain.model.company.CompanyId;
import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.repository.CompanyRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.CompanyJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataCompanyJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataRoleJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.CompanyMapper;
import bank_mega.corsys.infrastructure.adapter.out.mapper.RoleMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.CompanyPredicate;
import bank_mega.corsys.infrastructure.adapter.out.predicate.RolePredicate;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import bank_mega.corsys.infrastructure.util.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CompanyRepositoryImpl implements CompanyRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "cif",
            "name"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataCompanyJpaRepository springDataCompanyJpaRepository;

    @Override
    public Company save(Company company) {
        return CompanyMapper.toDomain(springDataCompanyJpaRepository.save(
                CompanyMapper.toJpaEntity(company)
        ));
    }

    @Override
    public void delete(Company company) {
        springDataCompanyJpaRepository.delete(CompanyMapper.toJpaEntity(company));
    }

    @Override
    public long count() {
        return springDataCompanyJpaRepository.count();
    }

    @Override
    public Page<@NonNull Company> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<CompanyJpaEntity> cQuery = cBuilder.createQuery(CompanyJpaEntity.class);
        Root<CompanyJpaEntity> root = cQuery.from(CompanyJpaEntity.class);

        // Filter and Sorting
        cQuery.where(CompanyPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<CompanyJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<CompanyJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<Company> list = resultList.stream()
                .map(CompanyMapper::toDomain)
                .toList();

        return new PageImpl<>(
            list,
            PageRequest.of(pageIndex, size),
            PersistenceUtil.count(
                entityManager,
                cBuilder,
                CompanyJpaEntity.class,
                (cBuilderCount, rootJpaCount) -> List.of(
                        CompanyPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                )
            )
        );
    }

    @Override
    public List<@NonNull Company> findAllByAuditDeletedAtIsNull() {
        return springDataCompanyJpaRepository.findAllByAuditDeletedAtIsNull().stream()
                .map(CompanyMapper::toDomain).toList();
    }

    @Override
    public Optional<Company> findFirstById(CompanyId companyId) {
        return springDataCompanyJpaRepository
                .findFirstById(companyId.value())
                .map(CompanyMapper::toDomain);
    }

    @Override
    public Optional<Company> findFirstByIdAndAuditDeletedAtIsNull(CompanyId companyId) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<CompanyJpaEntity> cQuery = cBuilder.createQuery(CompanyJpaEntity.class);
        Root<CompanyJpaEntity> root = cQuery.from(CompanyJpaEntity.class);

        // Filter Primary Key
        cQuery.where(CompanyPredicate.retrieveBuild(cBuilder, root, companyId));

        TypedQuery<CompanyJpaEntity> query = entityManager.createQuery(cQuery);
        List<CompanyJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(CompanyMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<Company> findFirstByCif(CompanyCif companyCif) {
        return springDataCompanyJpaRepository
                .findFirstByCif(companyCif.value())
                .map(CompanyMapper::toDomain);
    }
}
