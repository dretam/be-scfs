package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.common.Id;

public class DocumentNotFoundException extends DomainRuleViolationException {

    public DocumentNotFoundException(Id id) {
        super("Document with id '%s' not found".formatted(id.value()));
    }
}