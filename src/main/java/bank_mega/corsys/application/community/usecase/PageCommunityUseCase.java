package bank_mega.corsys.application.community.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.community.Community;
import bank_mega.corsys.domain.repository.CommunityRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class PageCommunityUseCase {

    private final CommunityRepository communityRepository;

    @Transactional(readOnly = true)
    public Page<@NonNull Community> execute(int page, int size, String sort, String filter) {
        return communityRepository.findAllPageable(page, size, sort, filter);
    }

}
