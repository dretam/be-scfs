package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.emailtemplate.EmailTemplate;
import bank_mega.corsys.domain.model.emailtemplate.EmailTemplateVariant;

import java.util.List;
import java.util.Optional;

public interface EmailTemplateRepository {
    EmailTemplate save(EmailTemplate emailTemplate);
    Optional<EmailTemplate> findByVariant(EmailTemplateVariant variant);
    List<EmailTemplate> findAll();
}
