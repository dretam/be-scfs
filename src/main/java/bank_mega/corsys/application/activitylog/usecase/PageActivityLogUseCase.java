package bank_mega.corsys.application.activitylog.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.activitylog.ActivityLog;
import bank_mega.corsys.domain.repository.ActivityLogRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@UseCase
@RequiredArgsConstructor
public class PageActivityLogUseCase {

    private final ActivityLogRepository activityLogRepository;

    @Transactional(readOnly = true)
    public Page<@NonNull ActivityLog> execute(int page, int size, Set<String> expands, String sort, String filter) {
        return activityLogRepository.findAllPageable(page, size, expands, sort, filter);
    }

}
