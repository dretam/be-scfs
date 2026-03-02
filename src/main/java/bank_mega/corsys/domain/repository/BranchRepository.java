package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.branch.Branch;
import bank_mega.corsys.domain.model.branch.BranchId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BranchRepository {


    long count();

    Page<@NonNull Branch> findAllPageable(int page, int size, String sort, String filter);

    Optional<Branch> findFirstById(BranchId id);

}
