package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.role.Role;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.model.role.RoleName;
import bank_mega.corsys.domain.repository.RoleRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.RoleJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.mapper.RoleMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.RolePredicate;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataRoleJpaRepository;
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
public class RoleRepositoryImpl implements RoleRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "name",
            "description"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataRoleJpaRepository springDataRoleJpaRepository;

    @Override
    public Role save(Role role) {
        return RoleMapper.toDomain(springDataRoleJpaRepository.save(
                RoleMapper.toJpaEntity(role)
        ));
    }

    @Override
    public void delete(Role role) {
        springDataRoleJpaRepository.delete(RoleMapper.toJpaEntity(role));
    }

    @Override
    public long count() {
        return springDataRoleJpaRepository.count();
    }

    @Override
    public Page<@NonNull Role> findAllPageable(int page, int size, Set<String> expands, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<RoleJpaEntity> cQuery = cBuilder.createQuery(RoleJpaEntity.class);
        Root<RoleJpaEntity> root = cQuery.from(RoleJpaEntity.class);

        // Expands fetching
        if (expands != null) {
            if (expands.contains("permissions")) {
                root.fetch("permissions", JoinType.LEFT);
            }
        }

        // Filter and Sorting
        cQuery.where(RolePredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<RoleJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<RoleJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<Role> list = resultList.stream()
                .map(jpa -> RoleMapper.toDomain(jpa, expands))
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        RoleJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                RolePredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<Role> findFirstById(RoleCode id) {
        return springDataRoleJpaRepository
                .findFirstById(id.value())
                .map(RoleMapper::toDomain);
    }

    @Override
    public Optional<Role> findFirstByIdAndAuditDeletedAtIsNull(RoleCode id) {
        return findFirstByIdAndAuditDeletedAtIsNull(id, null);
    }

    @Override
    public Optional<Role> findFirstByIdAndAuditDeletedAtIsNull(RoleCode id, Set<String> expands) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<RoleJpaEntity> cQuery = cBuilder.createQuery(RoleJpaEntity.class);
        Root<RoleJpaEntity> root = cQuery.from(RoleJpaEntity.class);

        // Expands fetching
        if (expands != null && !expands.isEmpty()) {
            if (expands.contains("permissions")) {
                root.fetch("permissions", JoinType.LEFT);
            }
        }

        // Filter Primary Key
        cQuery.where(RolePredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<RoleJpaEntity> query = entityManager.createQuery(cQuery);
        List<RoleJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(RoleMapper.toDomain(resultList.getFirst(), expands));
    }

    @Override
    public Optional<Role> findFirstByName(RoleName name) {
        return springDataRoleJpaRepository
                .findFirstByNameWithFetch(name.value())
                .map(RoleMapper::toDomain);
    }
}
