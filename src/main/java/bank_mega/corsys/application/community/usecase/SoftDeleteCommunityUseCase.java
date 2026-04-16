package bank_mega.corsys.application.community.usecase;

import bank_mega.corsys.application.assembler.CommunityAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.community.command.SoftDeleteCommunityCommand;
import bank_mega.corsys.application.community.dto.CommunityResponse;
import bank_mega.corsys.domain.exception.CommunityNotFoundException;
import bank_mega.corsys.domain.model.community.Community;
import bank_mega.corsys.domain.model.community.CommunityId;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteCommunityUseCase {

    private final CommunityRepository communityRepository;

    @Transactional
    public CommunityResponse execute(SoftDeleteCommunityCommand command, User authPrincipal) {

        Community community = communityRepository.findFirstByIdAndAuditDeletedAtIsNull(new CommunityId(command.communityId()))
                .orElseThrow(() -> new CommunityNotFoundException(new CommunityId(command.communityId())));

        community.deleteAudit(authPrincipal.getId().value());

        Community saved = communityRepository.save(community);

        return CommunityAssembler.toResponse(saved);
    }
}
