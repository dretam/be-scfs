package bank_mega.corsys.domain.exception;

import bank_mega.corsys.domain.model.community.CommunityId;
import bank_mega.corsys.domain.model.community.CommunityName;

public class CommunityNotFoundException extends DomainException {

    public CommunityNotFoundException(CommunityId communityId) {
        super("Community not found with id: " + communityId.value());
    }

    public CommunityNotFoundException(CommunityName communityName) {
        super("Community not found with name: " + communityName.value());
    }
}
