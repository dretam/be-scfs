package bank_mega.corsys.application.community.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.CommunityNotFoundException;
import bank_mega.corsys.domain.model.community.Community;
import bank_mega.corsys.domain.model.community.CommunityId;
import bank_mega.corsys.domain.repository.CommunityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@UseCase
@RequiredArgsConstructor
public class DeleteCommunityUseCase {

    private final CommunityRepository communityRepository;

    @Transactional
    public CommunityId execute(UUID id) {
        // 1. Validasi bahwa community ada
        Community community = communityRepository.findFirstById(new CommunityId(id)).orElseThrow(
                () -> new CommunityNotFoundException(new CommunityId(id))
        );

        // 2. Simpan di repository (masuk ke infra → mapper → jpa)
        communityRepository.delete(community);

        // 3. convert ke response DTO
        return new CommunityId(id);
    }
}
