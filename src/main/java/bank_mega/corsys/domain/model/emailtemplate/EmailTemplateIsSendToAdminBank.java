package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record EmailTemplateIsSendToAdminBank(Boolean value) {
    public EmailTemplateIsSendToAdminBank {
        if (value == null) {
            throw new DomainRuleViolationException("EmailTemplateIsSendToAdminBank cannot be null");
        }
    }
}
