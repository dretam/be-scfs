package bank_mega.corsys.application.accesslog.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.AccessLogNotFoundException;
import bank_mega.corsys.domain.model.accesslog.AccessLog;
import bank_mega.corsys.domain.model.accesslog.AccessLogId;
import bank_mega.corsys.domain.repository.AccessLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;


@UseCase
@RequiredArgsConstructor
public class RetrieveAccessLogUseCase {

    private final AccessLogRepository accessLogRepository;

    @Transactional(readOnly = true)
    public AccessLog execute(UUID id, Set<String> expands) {
        return accessLogRepository.findFirstById(new AccessLogId(id), expands).orElseThrow(
                () -> new AccessLogNotFoundException(new AccessLogId(id))
        );
    }

}
