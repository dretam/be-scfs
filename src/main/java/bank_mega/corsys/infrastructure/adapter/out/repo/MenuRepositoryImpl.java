package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.menu.Menu;
import bank_mega.corsys.domain.model.menu.MenuCode;
import bank_mega.corsys.domain.model.menu.MenuId;
import bank_mega.corsys.domain.model.role.RoleId;
import bank_mega.corsys.domain.repository.MenuRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.MenuJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataMenuJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.MenuMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.MenuPredicate;
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
public class MenuRepositoryImpl implements MenuRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "name",
            "code",
            "sortOrder"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataMenuJpaRepository springDataMenuJpaRepository;

    @Override
    public Menu save(Menu menu) {
        return MenuMapper.toDomain(springDataMenuJpaRepository.save(
                MenuMapper.toJpaEntity(menu)
        ));
    }

    @Override
    public void delete(Menu menu) {
        springDataMenuJpaRepository.delete(MenuMapper.toJpaEntity(menu));
    }

    @Override
    public long count() {
        return springDataMenuJpaRepository.count();
    }

    @Override
    public Page<@NonNull Menu> findAllPageable(int page, int size, String sort, String filter, Set<String> expands) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<MenuJpaEntity> cQuery = cBuilder.createQuery(MenuJpaEntity.class);
        Root<MenuJpaEntity> root = cQuery.from(MenuJpaEntity.class);

        if (expands != null) {
            if (expands.contains("permissions")) {
                root.fetch("permissions", JoinType.LEFT);
            }
        }
        
        cQuery.where(MenuPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<MenuJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<MenuJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<Menu> list = resultList.stream()
                .map(MenuMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        MenuJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                MenuPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public Optional<Menu> findFirstById(MenuId id) {
        return springDataMenuJpaRepository
                .findFirstById(id.value())
                .map(MenuMapper::toDomain);
    }

    @Override
    public Optional<Menu> findFirstByIdAndAuditDeletedAtIsNull(MenuId id) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<MenuJpaEntity> cQuery = cBuilder.createQuery(MenuJpaEntity.class);
        Root<MenuJpaEntity> root = cQuery.from(MenuJpaEntity.class);

        // Filter Primary Key
        cQuery.where(MenuPredicate.retrieveBuild(cBuilder, root, id));

        TypedQuery<MenuJpaEntity> query = entityManager.createQuery(cQuery);
        List<MenuJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(MenuMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<Menu> findFirstByCode(MenuCode code) {
        return springDataMenuJpaRepository
                .findFirstByCode(code.value())
                .map(MenuMapper::toDomain);
    }
}
