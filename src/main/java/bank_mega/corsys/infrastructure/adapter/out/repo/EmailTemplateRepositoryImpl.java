package bank_mega.corsys.infrastructure.adapter.out.repo;

import bank_mega.corsys.domain.model.emailtemplate.EmailTemplate;
import bank_mega.corsys.domain.model.emailtemplate.EmailTemplateVariant;
import bank_mega.corsys.domain.repository.EmailTemplateRepository;
import bank_mega.corsys.infrastructure.adapter.out.jpa.repository.SpringDataEmailTemplateJpaRepository;
import bank_mega.corsys.infrastructure.adapter.out.mapper.EmailTemplateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class EmailTemplateRepositoryImpl implements EmailTemplateRepository {

    private final SpringDataEmailTemplateJpaRepository springDataEmailTemplateJpaRepository;

    @Override
    public EmailTemplate save(EmailTemplate emailTemplate) {
        return EmailTemplateMapper.toDomain(springDataEmailTemplateJpaRepository.save(
                EmailTemplateMapper.toJpaEntity(emailTemplate)
        ));
    }

    @Override
    public Optional<EmailTemplate> findByVariant(EmailTemplateVariant variant) {
        return springDataEmailTemplateJpaRepository.findByVariant(variant)
                .map(EmailTemplateMapper::toDomain);
    }

    @Override
    public List<EmailTemplate> findAll() {
        return springDataEmailTemplateJpaRepository.findAllByAuditDeletedAtIsNull().stream()
                .map(EmailTemplateMapper::toDomain)
                .toList();
    }
}
