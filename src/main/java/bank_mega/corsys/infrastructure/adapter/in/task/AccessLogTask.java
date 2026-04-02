package bank_mega.corsys.infrastructure.adapter.in.task;

import bank_mega.corsys.application.activitylog.usecase.HousekeepingActivityLogUseCase;
import com.github.kagkarlsson.scheduler.task.TaskDescriptor;
import com.github.kagkarlsson.scheduler.task.helper.RecurringTask;
import com.github.kagkarlsson.scheduler.task.helper.Tasks;
import com.github.kagkarlsson.scheduler.task.schedule.Schedules;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;


@Component
public class AccessLogTask {

    public static final TaskDescriptor<Void> HK = TaskDescriptor.of(
            String.format("%s-%s", AccessLogTask.class.getSimpleName(), "housekeeping")
    );

    @Bean
    public RecurringTask<Void> housekeeping(HousekeepingActivityLogUseCase useCase) {
        return Tasks
                .recurring(HK, Schedules.cron("0 0 0 1 * *", ZoneId.systemDefault()))
                .execute((instance, ctx) -> useCase.execute(Instant.now()));
    }

}
