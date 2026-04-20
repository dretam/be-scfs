package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import java.util.UUID;

public record EmailTemplateVariableId(UUID value) {
    public EmailTemplateVariableId {
        if (value == null) {
            throw new DomainRuleViolationException("EmailTemplateVariableId cannot be null");
        }
    }

    public static EmailTemplateVariableId of(UUID value) {
        return new EmailTemplateVariableId(value);
    }
}
