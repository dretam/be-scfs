package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import java.util.UUID;

public record EmailTemplateId(UUID value) {
    public EmailTemplateId {
        if (value == null) {
            throw new DomainRuleViolationException("EmailTemplateId cannot be null");
        }
    }

    public static EmailTemplateId of(UUID value) {
        return new EmailTemplateId(value);
    }
}
