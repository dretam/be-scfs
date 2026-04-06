package bank_mega.corsys.domain.model.user;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

public record UserPhotoPath(String value) {
    public UserPhotoPath {
//        if (value == null || value.isBlank()) {
//            throw new DomainRuleViolationException("UserPhotoPath cannot be null or blank");
//        }
    }
}
