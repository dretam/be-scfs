package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.CommunityJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataCommunityJpaRepository extends JpaRepository<@NonNull CommunityJpaEntity, @NonNull UUID>,
        PagingAndSortingRepository<@NonNull CommunityJpaEntity, @NonNull UUID> {

    long count();

    List<@NonNull CommunityJpaEntity> findAllByAuditDeletedAtIsNull();

    Optional<CommunityJpaEntity> findFirstById(UUID id);

    Optional<CommunityJpaEntity> findFirstByName(String name);

}
