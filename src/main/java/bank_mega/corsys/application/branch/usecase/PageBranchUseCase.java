package bank_mega.corsys.application.branch.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.branch.Branch;
import bank_mega.corsys.domain.repository.BranchRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
public class PageBranchUseCase {

    private final BranchRepository branchRepository;

    @Transactional(readOnly = true)
    public Page<@NonNull Branch> execute(int page, int size, String sort, String filter) {
        return branchRepository.findAllPageable(page, size, sort, filter);
    }

}
