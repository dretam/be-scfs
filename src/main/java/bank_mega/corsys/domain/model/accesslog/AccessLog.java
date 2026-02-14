package bank_mega.corsys.domain.model.accesslog;

import bank_mega.corsys.domain.model.user.User;

import java.time.Instant;

public class AccessLog {

    private final AccessLogId id;
    private final User user;
    private final AccessLogIpAddress ipAddress;
    private final AccessLogUserAgent userAgent;
    private final AccessLogURI uri;
    private final AccessLogHttpMethod httpMethod;
    private final AccessLogQueryParam queryParam;
    private final AccessLogRequestBody requestBody;
    private final AccessLogStatusCode statusCode;
    private final AccessLogResponseTimeMs responseTimeMs;
    private final AccessLogErrorMessage errorMessage;
    private final Instant createdAt;

    public AccessLog(
            AccessLogId id,
            User user,
            AccessLogIpAddress ipAddress,
            AccessLogUserAgent userAgent,
            AccessLogURI uri,
            AccessLogHttpMethod httpMethod,
            AccessLogQueryParam queryParam,
            AccessLogRequestBody requestBody,
            AccessLogStatusCode statusCode,
            AccessLogResponseTimeMs responseTimeMs,
            AccessLogErrorMessage errorMessage,
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

    public AccessLogId getId() {
        return this.id;
    }

    public User getUser() {
        return this.user;
    }

    public AccessLogIpAddress getIpAddress() {
        return this.ipAddress;
    }

    public AccessLogUserAgent getUserAgent() {
        return this.userAgent;
    }

    public AccessLogURI getUri() {
        return this.uri;
    }

    public AccessLogHttpMethod getHttpMethod() {
        return this.httpMethod;
    }

    public AccessLogQueryParam getQueryParam() {
        return this.queryParam;
    }

    public AccessLogRequestBody getRequestBody() {
        return this.requestBody;
    }

    public AccessLogStatusCode getStatusCode() {
        return this.statusCode;
    }

    public AccessLogResponseTimeMs getResponseTimeMs() {
        return this.responseTimeMs;
    }

    public AccessLogErrorMessage getErrorMessage() {
        return this.errorMessage;
    }

    public Instant getCreatedAt() {
        return this.createdAt;
    }

}
