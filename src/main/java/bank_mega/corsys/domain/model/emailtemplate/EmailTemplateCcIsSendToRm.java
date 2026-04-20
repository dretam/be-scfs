package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record EmailTemplateCcIsSendToRm(Boolean value) {
    public EmailTemplateCcIsSendToRm {
        if (value == null) {
            throw new DomainRuleViolationException("EmailTemplateCcIsSendToRm cannot be null");
        }
    }
}
