package bank_mega.corsys.application.community.usecase;

import bank_mega.corsys.application.assembler.CommunityAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.community.dto.CommunityResponse;
import bank_mega.corsys.domain.exception.CommunityNotFoundException;
import bank_mega.corsys.domain.model.community.Community;
import bank_mega.corsys.domain.model.community.CommunityId;
import bank_mega.corsys.domain.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class RetrieveCommunityUseCase {

    private final CommunityRepository communityRepository;

    @Transactional(readOnly = true)
    public CommunityResponse execute(UUID id) {
        Community community = communityRepository.findFirstByIdAndAuditDeletedAtIsNull(new CommunityId(id)).orElseThrow(
                () -> new CommunityNotFoundException(new CommunityId(id))
        );

        return CommunityAssembler.toResponse(community);
    }

}
