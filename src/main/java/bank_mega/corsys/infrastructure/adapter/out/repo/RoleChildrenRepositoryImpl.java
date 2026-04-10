package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.domain.model.rolechildren.RoleChildren;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenCode;
import bank_mega.corsys.domain.model.rolechildren.RoleChildrenName;
import bank_mega.corsys.domain.repository.RoleChildrenRepository;
import bank_mega.corsys.domain.repository.RoleRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleChildrenJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataRoleChildrenJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataRoleJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.RoleChildrenMapper;
import bank_mega.corsys.infrastructure.adapter.out.mapper.RoleMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.RoleChildrenPredicate;
import bank_mega.corsys.infrastructure.adapter.out.predicate.RolePredicate;
import bank_mega.corsys.infrastructure.util.ParserUtil;
import bank_mega.corsys.infrastructure.util.PersistenceUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
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
import java.util.Set;


@Repository
@RequiredArgsConstructor
public class RoleChildrenRepositoryImpl implements RoleChildrenRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "name",
            "description"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataRoleChildrenJpaRepository springDataRoleChildrenJpaRepository;

    @Override
    public RoleChildren save(RoleChildren roleChildren) {
        return RoleChildrenMapper.toDomain(springDataRoleChildrenJpaRepository.save(
                RoleChildrenMapper.toJpaEntity(roleChildren)
        ));
    }

    @Override
    public void delete(RoleChildren roleChildren) {
        springDataRoleChildrenJpaRepository.delete(RoleChildrenMapper.toJpaEntity(roleChildren));
    }

    @Override
    public long count() {
        return springDataRoleChildrenJpaRepository.count();
    }

    @Override
    public Page<@NonNull RoleChildren> findAllPageable(int page, int size, Set<String> expands, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<RoleChildrenJpaEntity> cQuery = cBuilder.createQuery(RoleChildrenJpaEntity.class);
        Root<RoleChildrenJpaEntity> root = cQuery.from(RoleChildrenJpaEntity.class);

        // Expands fetching
        if (expands != null) {
            if (expands.contains("permissions")) {
                root.fetch("permissions", JoinType.LEFT);
            }
        }

        // Filter and Sorting
        cQuery.where(RoleChildrenPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<RoleChildrenJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<RoleChildrenJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<RoleChildren> list = resultList.stream()
                .map(jpa -> RoleChildrenMapper.toDomain(jpa, expands))
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        RoleChildrenJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                RoleChildrenPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public List<@NonNull RoleChildren> findAllByAuditDeletedAtIsNull() {
        return springDataRoleChildrenJpaRepository.findAllByAuditDeletedAtIsNull().stream()
                .map(RoleChildrenMapper::toDomain).toList();
    }

    @Override
    public Optional<RoleChildren> findFirstById(RoleChildrenCode id) {
        return springDataRoleChildrenJpaRepository
                .findFirstByCode(id.value())
                .map(RoleChildrenMapper::toDomain);
    }

    @Override
    public Optional<RoleChildren> findFirstByIdAndAuditDeletedAtIsNull(RoleChildrenCode id) {
        return findFirstByIdAndAuditDeletedAtIsNull(id, null);
    }

    @Override
    public Optional<RoleChildren> findFirstByIdAndAuditDeletedAtIsNull(RoleChildrenCode id, Set<String> expands) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<RoleChildrenJpaEntity> cQuery = cBuilder.createQuery(RoleChildrenJpaEntity.class);
        Root<RoleChildrenJpaEntity> root = cQuery.from(RoleChildrenJpaEntity.class);

        // Expands fetching
        if (expands != null && !expands.isEmpty()) {
            if (expands.contains("permissions")) {
                root.fetch("permissions", JoinType.LEFT);
            }
        }

        // Filter Primary Key
        cQuery.where(RoleChildrenPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<RoleChildrenJpaEntity> query = entityManager.createQuery(cQuery);
        List<RoleChildrenJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(RoleChildrenMapper.toDomain(resultList.getFirst(), expands));
    }

    @Override
    public Optional<RoleChildren> findFirstByName(RoleChildrenName name) {
        return springDataRoleChildrenJpaRepository
                .findFirstByNameWithFetch(name.value())
                .map(RoleChildrenMapper::toDomain);
    }
}
