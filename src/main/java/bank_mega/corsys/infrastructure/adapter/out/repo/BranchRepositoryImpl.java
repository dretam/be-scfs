package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.branch.Branch;
import bank_mega.corsys.domain.model.branch.BranchId;
import bank_mega.corsys.domain.repository.BranchRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.BranchJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.mapper.BranchMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.BranchPredicate;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataBranchJpaRepository;
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
public class BranchRepositoryImpl implements BranchRepository {

    private final String[] availableSort = {
            "idBranch",
            "branchName",
            "category",
            "regional",
            "area",
            "audit.createdAt"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataBranchJpaRepository springDataBranchJpaRepository;

    @Override
    public long count() {
        return springDataBranchJpaRepository.count();
    }

    @Override
    public Page<@NonNull Branch> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<BranchJpaEntity> cQuery = cBuilder.createQuery(BranchJpaEntity.class);
        Root<BranchJpaEntity> root = cQuery.from(BranchJpaEntity.class);

        // Filter and Sorting
        cQuery.where(BranchPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<BranchJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<BranchJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<Branch> list = resultList.stream()
                .map(BranchMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        BranchJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                BranchPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<Branch> findFirstById(BranchId id) {
        return springDataBranchJpaRepository
                .findFirstById(id.value())
                .map(BranchMapper::toDomain);
    }
}
