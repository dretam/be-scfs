package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record EmailTemplateIsActive(Boolean value) {
    public EmailTemplateIsActive {
        if (value == null) {
            throw new DomainRuleViolationException("EmailTemplateIsActive cannot be null");
        }
    }
}
