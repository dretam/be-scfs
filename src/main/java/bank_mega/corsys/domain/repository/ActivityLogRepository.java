package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.activitylog.ActivityLog;
import bank_mega.corsys.domain.model.activitylog.ActivityLogId;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ActivityLogRepository {

    ActivityLog save(ActivityLog accessLog);

    long count();

    void housekeeping(Instant time);

    Page<@NonNull ActivityLog> findAllPageable(int page, int size, Set<String> expand, String sort, String filter);

    Optional<ActivityLog> findFirstById(ActivityLogId id, Set<String> expand);

}
