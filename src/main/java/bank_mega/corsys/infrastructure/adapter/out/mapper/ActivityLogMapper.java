package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.activitylog.*;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.ActivityLogJpaEntity;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class ActivityLogMapper {

    public static ActivityLog toDomain(@NotNull ActivityLogJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null (AccessLogMapper)");
        return new ActivityLog(
                new ActivityLogId(jpaEntity.getId()),
                UserMapper.toDomain(jpaEntity.getUser()),
                new ActivityLogIpAddress(jpaEntity.getIpAddress()),
                new ActivityLogUserAgent(jpaEntity.getUserAgent()),
                new ActivityLogURI(jpaEntity.getUri()),
                jpaEntity.getHttpMethod(),
                new ActivityLogQueryParam(jpaEntity.getQueryParam()),
                new ActivityLogRequestBody(jpaEntity.getRequestBody()),
                new ActivityLogStatusCode(jpaEntity.getStatusCode()),
                new ActivityLogResponseTimeMs(jpaEntity.getResponseTimeMs()),
                new ActivityLogErrorMessage(jpaEntity.getErrorMessage()),
                jpaEntity.getCreatedAt()
        );
    }

    public static ActivityLog toDomain(@NotNull ActivityLogJpaEntity jpaEntity, Set<String> expands) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null (AccessLogMapper)");
        return new ActivityLog(
                new ActivityLogId(jpaEntity.getId()),
                expands.contains("user") && jpaEntity.getUser() != null
                        ? UserMapper.toDomain(jpaEntity.getUser())
                        : null,
                new ActivityLogIpAddress(jpaEntity.getIpAddress()),
                new ActivityLogUserAgent(jpaEntity.getUserAgent()),
                new ActivityLogURI(jpaEntity.getUri()),
                jpaEntity.getHttpMethod(),
                new ActivityLogQueryParam(jpaEntity.getQueryParam()),
                new ActivityLogRequestBody(jpaEntity.getRequestBody()),
                new ActivityLogStatusCode(jpaEntity.getStatusCode()),
                new ActivityLogResponseTimeMs(jpaEntity.getResponseTimeMs()),
                new ActivityLogErrorMessage(jpaEntity.getErrorMessage()),
                jpaEntity.getCreatedAt()
        );
    }

    public static ActivityLogJpaEntity toJpaEntity(ActivityLog accessLog) {
        ActivityLogJpaEntity jpaEntity = new ActivityLogJpaEntity();
        if (accessLog.getId() != null) {
            jpaEntity.setId(accessLog.getId().value());
        }
        jpaEntity.setUser(UserMapper.toJpaEntity(accessLog.getUser()));
        jpaEntity.setIpAddress(accessLog.getIpAddress().value());
        jpaEntity.setUserAgent(accessLog.getUserAgent().value());
        jpaEntity.setUri(accessLog.getUri().value());
        jpaEntity.setHttpMethod(accessLog.getHttpMethod());
        jpaEntity.setQueryParam(accessLog.getQueryParam().value());
        jpaEntity.setRequestBody(accessLog.getRequestBody().value());
        jpaEntity.setResponseTimeMs(accessLog.getResponseTimeMs().value());
        jpaEntity.setStatusCode(accessLog.getStatusCode().value());
        jpaEntity.setErrorMessage(accessLog.getErrorMessage().value());
        jpaEntity.setCreatedAt(accessLog.getCreatedAt());
        return jpaEntity;
    }

}
