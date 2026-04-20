package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record EmailTemplateSubject(String value) {
    public EmailTemplateSubject {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("EmailTemplateSubject cannot be null or empty");
        }
    }
}
