package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record EmailTemplateVariableFlag(String value) {
    public EmailTemplateVariableFlag {
        if (value == null || value.isBlank()) {
            throw new DomainRuleViolationException("EmailTemplateVariableFlag cannot be null or empty");
        }
    }
}
