package bank_mega.corsys.application.activitylog.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.AccessLogNotFoundException;
import bank_mega.corsys.domain.model.activitylog.ActivityLog;
import bank_mega.corsys.domain.model.activitylog.ActivityLogId;
import bank_mega.corsys.domain.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;


@UseCase
@RequiredArgsConstructor
public class RetrieveActivityLogUseCase {

    private final ActivityLogRepository activityLogRepository;

    @Transactional(readOnly = true)
    public ActivityLog execute(UUID id, Set<String> expands) {
        return activityLogRepository.findFirstById(new ActivityLogId(id), expands).orElseThrow(
                () -> new AccessLogNotFoundException(new ActivityLogId(id))
        );
    }

}
