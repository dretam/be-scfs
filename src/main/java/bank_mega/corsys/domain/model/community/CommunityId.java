package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;

import java.util.UUID;

public record CommunityId(UUID value) {

    public CommunityId {
        if (value == null) {
            throw new DomainRuleViolationException("CommunityId cannot be null");
        }
    }

    public static CommunityId of(UUID value) {
        return new CommunityId(value);
    }

}
