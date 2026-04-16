package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.community.Community;
import bank_mega.corsys.domain.model.community.CommunityId;
import bank_mega.corsys.domain.model.community.CommunityName;
import bank_mega.corsys.domain.repository.CommunityRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.CommunityJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataCommunityJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.CommunityMapper;
import bank_mega.corsys.infrastructure.adapter.out.predicate.CommunityPredicate;
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
public class CommunityRepositoryImpl implements CommunityRepository {

    private final String[] availableSort = {
            "audit.createdAt",
            "name"
    };

    @PersistenceContext
    private EntityManager entityManager;

    private final SpringDataCommunityJpaRepository springDataCommunityJpaRepository;

    @Override
    public Community save(Community community) {
        return CommunityMapper.toDomain(springDataCommunityJpaRepository.save(
                CommunityMapper.toJpaEntity(community)
        ));
    }

    @Override
    public void delete(Community community) {
        springDataCommunityJpaRepository.delete(CommunityMapper.toJpaEntity(community));
    }

    @Override
    public long count() {
        return springDataCommunityJpaRepository.count();
    }

    @Override
    public Page<@NonNull Community> findAllPageable(int page, int size, String sort, String filter) {
        int pageIndex = page - 1;
        Sort sortBy = ParserUtil.sortParse(this.availableSort, sort, this.availableSort[0]);
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<CommunityJpaEntity> cQuery = cBuilder.createQuery(CommunityJpaEntity.class);
        Root<CommunityJpaEntity> root = cQuery.from(CommunityJpaEntity.class);

        // Filter and Sorting
        cQuery.where(CommunityPredicate.listBuild(cBuilder, root, filter));
        cQuery.orderBy(ParserUtil.toOrders(sortBy, cBuilder, root));

        TypedQuery<CommunityJpaEntity> query = entityManager.createQuery(cQuery);

        // Pagination Offset
        query.setFirstResult(pageIndex * size);
        query.setMaxResults(size);
        List<CommunityJpaEntity> resultList = query.getResultList();

        // Mapping ke domain
        List<Community> list = resultList.stream()
                .map(CommunityMapper::toDomain)
                .toList();

        return new PageImpl<>(
                list,
                PageRequest.of(pageIndex, size),
                PersistenceUtil.count(
                        entityManager,
                        cBuilder,
                        CommunityJpaEntity.class,
                        (cBuilderCount, rootJpaCount) -> List.of(
                                CommunityPredicate.listBuild(cBuilderCount, rootJpaCount, filter)
                        )
                )
        );
    }

    @Override
    public List<@NonNull Community> findAllByAuditDeletedAtIsNull() {
        return springDataCommunityJpaRepository.findAllByAuditDeletedAtIsNull().stream()
                .map(CommunityMapper::toDomain).toList();
    }

    @Override
    public Optional<Community> findFirstById(CommunityId communityId) {
        return springDataCommunityJpaRepository
                .findFirstById(communityId.value())
                .map(CommunityMapper::toDomain);
    }

    @Override
    public Optional<Community> findFirstByIdAndAuditDeletedAtIsNull(CommunityId communityId) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<CommunityJpaEntity> cQuery = cBuilder.createQuery(CommunityJpaEntity.class);
        Root<CommunityJpaEntity> root = cQuery.from(CommunityJpaEntity.class);

        // Filter Primary Key
        cQuery.where(CommunityPredicate.retrieveBuild(cBuilder, root, communityId));

        TypedQuery<CommunityJpaEntity> query = entityManager.createQuery(cQuery);
        List<CommunityJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(CommunityMapper.toDomain(resultList.getFirst()));
    }

    @Override
    public Optional<Community> findFirstByName(CommunityName communityName) {
        return springDataCommunityJpaRepository
                .findFirstByName(communityName.value())
                .map(CommunityMapper::toDomain);
    }

    @Override
    public Optional<Community> findFirstByNameAndAuditDeletedAtIsNull(CommunityName communityName) {
        CriteriaBuilder cBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<CommunityJpaEntity> cQuery = cBuilder.createQuery(CommunityJpaEntity.class);
        Root<CommunityJpaEntity> root = cQuery.from(CommunityJpaEntity.class);

        // Filter By Name
        cQuery.where(CommunityPredicate.retrieveByNameBuild(cBuilder, root, communityName));

        TypedQuery<CommunityJpaEntity> query = entityManager.createQuery(cQuery);
        List<CommunityJpaEntity> resultList = query.getResultList();
        if (resultList.isEmpty()) return Optional.empty();
        return Optional.of(CommunityMapper.toDomain(resultList.getFirst()));
    }
}
