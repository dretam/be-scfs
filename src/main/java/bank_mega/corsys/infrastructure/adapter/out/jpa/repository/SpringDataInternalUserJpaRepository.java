package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.InternalUserJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataInternalUserJpaRepository extends JpaRepository<@NonNull InternalUserJpaEntity, @NonNull String>, PagingAndSortingRepository<@NonNull InternalUserJpaEntity, @NonNull String> {

    long count();

    Optional<InternalUserJpaEntity> findFirstByUserName(String userName);

    Optional<InternalUserJpaEntity> findFirstByEmail(String email);

    Optional<InternalUserJpaEntity> findFirstByEmailAndUserNameNot(String email, String userName);
}
