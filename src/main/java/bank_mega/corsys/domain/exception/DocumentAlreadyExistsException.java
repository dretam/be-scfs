package bank_mega.corsys.domain.exception;

public class DocumentAlreadyExistsException extends DomainRuleViolationException {

    public DocumentAlreadyExistsException(String filename) {
        super("Document with filename '%s' already exists".formatted(filename));
    }
}