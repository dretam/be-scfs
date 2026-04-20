package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record EmailTemplateIsSendToSupplier(Boolean value) {
    public EmailTemplateIsSendToSupplier {
        if (value == null) {
            throw new DomainRuleViolationException("EmailTemplateIsSendToSupplier cannot be null");
        }
    }
}
