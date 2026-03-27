package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.permission.Permission;
import bank_mega.corsys.domain.model.permission.PermissionCode;
import bank_mega.corsys.domain.model.permission.PermissionId;
import bank_mega.corsys.domain.model.role.RoleCode;
import bank_mega.corsys.domain.repository.PermissionRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.PermissionJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataPermissionJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.PermissionMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.PermissionPredicate;
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
public class PermissionRepositoryImpl implements PermissionRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "name",
            "code",
            "description"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataPermissionJpaRepository springDataPermissionJpaRepository;

    @Override
    public Permission save(Permission permission) {
        return PermissionMapper.toDomain(springDataPermissionJpaRepository.save(
                PermissionMapper.toJpaEntity(permission)
        ));
    }

    @Override
    public void delete(Permission permission) {
        springDataPermissionJpaRepository.delete(PermissionMapper.toJpaEntity(permission));
    }

    @Override
    public long count() {
        return springDataPermissionJpaRepository.count();
    }

    @Override
    public Page<@NonNull Permission> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<PermissionJpaEntity> cQuery = cBuilder.createQuery(PermissionJpaEntity.class);
        Root<PermissionJpaEntity> root = cQuery.from(PermissionJpaEntity.class);

        // Filter and Sorting
        cQuery.where(PermissionPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<PermissionJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<PermissionJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<Permission> list = resultList.stream()
                .map(PermissionMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        PermissionJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                PermissionPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<Permission> findFirstById(PermissionId id) {
        return springDataPermissionJpaRepository
                .findFirstById(id.value())
                .map(PermissionMapper::toDomain);
    }

    @Override
    public Optional<Permission> findFirstByIdAndAuditDeletedAtIsNull(PermissionId id) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<PermissionJpaEntity> cQuery = cBuilder.createQuery(PermissionJpaEntity.class);
        Root<PermissionJpaEntity> root = cQuery.from(PermissionJpaEntity.class);

        // Filter Primary Key
        cQuery.where(PermissionPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<PermissionJpaEntity> query = entityManager.createQuery(cQuery);
        List<PermissionJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(PermissionMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<Permission> findFirstByCode(PermissionCode code) {
        return springDataPermissionJpaRepository
                .findFirstByCode(code.value())
                .map(PermissionMapper::toDomain);
    }

    @Override
    public List<Permission> findAllByRoleId(RoleCode roleCode) {
        return springDataPermissionJpaRepository.findAllByRoleId(roleCode.value()).stream()
                .map(PermissionMapper::toDomain)
                .toList();
    }

}
