package bank_mega.corsys.application.activitylog.usecase;

import bank_mega.corsys.application.activitylog.command.CreateActivityLogCommand;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.activitylog.*;
import bank_mega.corsys.domain.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CreateActivityLogUseCase {

    private final ActivityLogRepository activityLogRepository;

    @Transactional
    public ActivityLog execute(CreateActivityLogCommand command) {
        return this.activityLogRepository.save(new ActivityLog(
                null,
                command.user(),
                new ActivityLogIpAddress(command.ipAddress()),
                new ActivityLogUserAgent(command.userAgent()),
                new ActivityLogURI(command.uri()),
                ActivityLogHttpMethod.valueOf(command.httpMethod()),
                new ActivityLogQueryParam(command.queryParams()),
                new ActivityLogRequestBody(command.requestBody()),
                new ActivityLogStatusCode(command.statusCode()),
                new ActivityLogResponseTimeMs(command.responseTimeMs()),
                new ActivityLogErrorMessage(command.errorMessage()),
                command.createdAt()
        ));
    }


}
