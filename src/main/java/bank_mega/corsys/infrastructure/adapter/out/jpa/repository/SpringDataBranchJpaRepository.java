package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.BranchJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataBranchJpaRepository extends JpaRepository<@NonNull BranchJpaEntity, @NonNull Long>, PagingAndSortingRepository<@NonNull BranchJpaEntity, @NonNull Long> {

    long count();

    Optional<BranchJpaEntity> findFirstById(Long id);
}
