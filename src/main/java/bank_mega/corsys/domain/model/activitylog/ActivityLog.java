package bank_mega.corsys.domain.model.activitylog;

import bank_mega.corsys.domain.model.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ActivityLog {

    private final ActivityLogId id;
    private final User user;
    private final ActivityLogIpAddress ipAddress;
    private final ActivityLogUserAgent userAgent;
    private final ActivityLogURI uri;
    private final ActivityLogHttpMethod httpMethod;
    private final ActivityLogQueryParam queryParam;
    private final ActivityLogRequestBody requestBody;
    private final ActivityLogStatusCode statusCode;
    private final ActivityLogResponseTimeMs responseTimeMs;
    private final ActivityLogErrorMessage errorMessage;
    private final Instant createdAt;

    public ActivityLog(
            ActivityLogId id,
            User user,
            ActivityLogIpAddress ipAddress,
            ActivityLogUserAgent userAgent,
            ActivityLogURI uri,
            ActivityLogHttpMethod httpMethod,
            ActivityLogQueryParam queryParam,
            ActivityLogRequestBody requestBody,
            ActivityLogStatusCode statusCode,
            ActivityLogResponseTimeMs responseTimeMs,
            ActivityLogErrorMessage errorMessage,
            Instant createdAt
    ) {
        this.id = id;
        this.user = user;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.queryParam = queryParam;
        this.requestBody = requestBody;
        this.statusCode = statusCode;
        this.responseTimeMs = responseTimeMs;
        this.errorMessage = errorMessage;
        this.createdAt = createdAt;
    }

    public ActivityLogId getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public ActivityLogIpAddress getIpAddress() {
        return this.ipAddress;
    }

    public ActivityLogUserAgent getUserAgent() {
        return this.userAgent;
    }

    public ActivityLogURI getUri() {
        return this.uri;
    }

    public ActivityLogHttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public ActivityLogQueryParam getQueryParam() {
        return this.queryParam;
    }

    public ActivityLogRequestBody getRequestBody() {
        return this.requestBody;
    }

    public ActivityLogStatusCode getStatusCode() {
        return this.statusCode;
    }

    public ActivityLogResponseTimeMs getResponseTimeMs() {
        return this.responseTimeMs;
    }

    public ActivityLogErrorMessage getErrorMessage() {
        return this.errorMessage;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

}
