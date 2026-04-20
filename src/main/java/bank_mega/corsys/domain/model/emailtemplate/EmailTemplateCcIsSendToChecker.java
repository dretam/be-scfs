package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record EmailTemplateCcIsSendToChecker(Boolean value) {
    public EmailTemplateCcIsSendToChecker {
        if (value == null) {
            throw new DomainRuleViolationException("EmailTemplateCcIsSendToChecker cannot be null");
        }
    }
}
