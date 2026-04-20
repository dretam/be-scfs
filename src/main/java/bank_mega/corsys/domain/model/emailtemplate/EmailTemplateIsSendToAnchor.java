package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record EmailTemplateIsSendToAnchor(Boolean value) {
    public EmailTemplateIsSendToAnchor {
        if (value == null) {
            throw new DomainRuleViolationException("EmailTemplateIsSendToAnchor cannot be null");
        }
    }
}
