package bank_mega.corsys.application.accesslog.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.accesslog.*;
import bank_mega.corsys.domain.repository.AccessLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@UseCase
@RequiredArgsConstructor
public class HousekeepingAccessLogUseCase {

    private final AccessLogRepository accessLogRepository;

    @Transactional
    public void execute(Instant time) {
        accessLogRepository.housekeeping(time);
    }


}
