package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import java.util.UUID;

public record EmailTemplateSpecificEmailId(UUID value) {
    public EmailTemplateSpecificEmailId {
        if (value == null) {
            throw new DomainRuleViolationException("EmailTemplateSpecificEmailId cannot be null");
        }
    }

    public static EmailTemplateSpecificEmailId of(UUID value) {
        return new EmailTemplateSpecificEmailId(value);
    }
}
