package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.emailtemplate.EmailTemplateVariant;

public class EmailTemplateNotFoundException extends DomainException {

    public EmailTemplateNotFoundException(EmailTemplateVariant variant) {
        super("Email template not found with variant: " + variant.name());
    }
}
