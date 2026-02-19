package bank_mega.corsys.application.accesslog.usecase;

import bank_mega.corsys.application.accesslog.command.CreateAccessLogCommand;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.accesslog.*;
import bank_mega.corsys.domain.repository.AccessLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class CreateAccessLogUseCase {

    private final AccessLogRepository accessLogRepository;

    @Transactional
    public AccessLog execute(CreateAccessLogCommand command) {
        return this.accessLogRepository.save(new AccessLog(
                null,
                command.user(),
                new AccessLogIpAddress(command.ipAddress()),
                new AccessLogUserAgent(command.userAgent()),
                new AccessLogURI(command.uri()),
                AccessLogHttpMethod.valueOf(command.httpMethod()),
                new AccessLogQueryParam(command.queryParams()),
                new AccessLogRequestBody(command.requestBody()),
                new AccessLogStatusCode(command.statusCode()),
                new AccessLogResponseTimeMs(command.responseTimeMs()),
                new AccessLogErrorMessage(command.errorMessage()),
                command.createdAt()
        ));
    }


}
