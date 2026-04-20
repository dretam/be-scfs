package bank_mega.corsys.domain.model.emailtemplate;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record EmailTemplateCcIsSendToSpecifiedMail(Boolean value) {
    public EmailTemplateCcIsSendToSpecifiedMail {
        if (value == null) {
            throw new DomainRuleViolationException("EmailTemplateCcIsSendToSpecifiedMail cannot be null");
        }
    }
}
