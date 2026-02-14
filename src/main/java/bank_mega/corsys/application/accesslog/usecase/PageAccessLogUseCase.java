package bank_mega.corsys.application.accesslog.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.accesslog.AccessLog;
import bank_mega.corsys.domain.repository.AccessLogRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@UseCase
@RequiredArgsConstructor
public class PageAccessLogUseCase {

    private final AccessLogRepository accessLogRepository;

    @Transactional(readOnly = true)
    public Page<@NonNull AccessLog> execute(int page, int size, Set<String> expands, String sort, String filter) {
        return accessLogRepository.findAllPageable(page, size, expands, sort, filter);
    }

}
