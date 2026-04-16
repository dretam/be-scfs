package bank_mega.corsys.application.community.command;

import lombok.Builder;

import java.util.UUID;

@Builder
public record SoftDeleteCommunityCommand(
        UUID communityId
) {
}
