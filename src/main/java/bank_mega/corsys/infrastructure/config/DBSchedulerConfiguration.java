package bank_mega.corsys.infrastructure.config;

import com.github.kagkarlsson.scheduler.SchedulerClient;
import com.github.kagkarlsson.scheduler.event.AbstractSchedulerListener;
import com.github.kagkarlsson.scheduler.event.SchedulerListener;
import com.github.kagkarlsson.scheduler.task.ExecutionComplete;
import com.github.kagkarlsson.scheduler.task.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class DBSchedulerConfiguration {


    @Bean
    public SchedulerClient schedulerClient(DataSource dataSource, Task<?> taskInstance) {
        return SchedulerClient.Builder.create(dataSource, taskInstance).build();
    }

    @Bean
    SchedulerListener schedulerListener() {
        return new AbstractSchedulerListener() {

            @Override
            public void onExecutionComplete(ExecutionComplete executionComplete) {
                log.info(
                        "SchedulerListener.onExecutionComplete. Result={}, took={}ms ",
                        executionComplete.getResult(),
                        executionComplete.getDuration().toMillis());
            }
        };
    }

}
