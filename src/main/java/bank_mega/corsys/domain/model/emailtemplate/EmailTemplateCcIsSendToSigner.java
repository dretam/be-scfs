package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record EmailTemplateCcIsSendToSigner(Boolean value) {
    public EmailTemplateCcIsSendToSigner {
        if (value == null) {
            throw new DomainRuleViolationException("EmailTemplateCcIsSendToSigner cannot be null");
        }
    }
}
