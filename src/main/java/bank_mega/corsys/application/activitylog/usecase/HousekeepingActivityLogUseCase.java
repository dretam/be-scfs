package bank_mega.corsys.application.activitylog.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@UseCase
@RequiredArgsConstructor
public class HousekeepingActivityLogUseCase {

    private final ActivityLogRepository activityLogRepository;

    @Transactional
    public void execute(Instant time) {
        activityLogRepository.housekeeping(time);
    }


}
