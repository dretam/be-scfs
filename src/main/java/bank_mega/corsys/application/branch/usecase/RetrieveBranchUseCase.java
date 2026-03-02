package bank_mega.corsys.application.branch.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.BranchNotFoundException;
import bank_mega.corsys.domain.model.branch.Branch;
import bank_mega.corsys.domain.model.branch.BranchId;
import bank_mega.corsys.domain.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;


@UseCase
@RequiredArgsConstructor
public class RetrieveBranchUseCase {

    private final BranchRepository branchRepository;

    @Transactional(readOnly = true)
    public Branch execute(Long id) {
        return branchRepository.findFirstById(new BranchId(id)).orElseThrow(
                () -> new BranchNotFoundException(new BranchId(id))
        );
    }

}
