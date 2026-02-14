package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.accesslog.AccessLog;
import bank_mega.corsys.domain.model.accesslog.AccessLogId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

public interface AccessLogRepository {

    AccessLog save(AccessLog accessLog);

    long count();

    void housekeeping(Instant time);

    Page<@NonNull AccessLog> findAllPageable(int page, int size, Set<String> expand, String sort, String filter);

    Optional<AccessLog> findFirstById(AccessLogId id, Set<String> expand);

}
