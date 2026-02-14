package bank_mega.corsys.domain.exception;

public class DomainRuleViolationException extends DomainException {

    public DomainRuleViolationException(String message) {
        super("Domain rule violation: " + message);
    }

}
