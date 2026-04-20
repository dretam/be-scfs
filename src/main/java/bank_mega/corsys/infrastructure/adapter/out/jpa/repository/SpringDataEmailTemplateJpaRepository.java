package bank_mega.corsys.infrastructure.adapter.out.jpa.repository;

import bank_mega.corsys.domain.model.emailtemplate.EmailTemplateVariant;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.EmailTemplateJpaEntity;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataEmailTemplateJpaRepository extends JpaRepository<@NonNull EmailTemplateJpaEntity, @NonNull UUID>,
        PagingAndSortingRepository<@NonNull EmailTemplateJpaEntity, @NonNull UUID> {

    List<@NonNull EmailTemplateJpaEntity> findAllByAuditDeletedAtIsNull();

    Optional<EmailTemplateJpaEntity> findByVariant(@NonNull EmailTemplateVariant variant);
}
