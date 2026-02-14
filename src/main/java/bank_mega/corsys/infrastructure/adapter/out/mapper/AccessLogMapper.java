package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.accesslog.*;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.AccessLogJpaEntity;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public class AccessLogMapper {

    public static AccessLog toDomain(@NotNull AccessLogJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");
        return new AccessLog(
                new AccessLogId(jpaEntity.getId()),
                UserMapper.toDomain(jpaEntity.getUser()),
                new AccessLogIpAddress(jpaEntity.getIpAddress()),
                new AccessLogUserAgent(jpaEntity.getUserAgent()),
                new AccessLogURI(jpaEntity.getUri()),
                jpaEntity.getHttpMethod(),
                new AccessLogQueryParam(jpaEntity.getQueryParams()),
                new AccessLogRequestBody(jpaEntity.getRequestBody()),
                new AccessLogStatusCode(jpaEntity.getStatusCode()),
                new AccessLogResponseTimeMs(jpaEntity.getResponseTimeMs()),
                new AccessLogErrorMessage(jpaEntity.getErrorMessage()),
                jpaEntity.getCreatedAt()
        );
    }

    public static AccessLog toDomain(@NotNull AccessLogJpaEntity jpaEntity, Set<String> expands) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");
        return new AccessLog(
                new AccessLogId(jpaEntity.getId()),
                expands.contains("user") && jpaEntity.getUser() != null
                        ? UserMapper.toDomain(jpaEntity.getUser())
                        : null,
                new AccessLogIpAddress(jpaEntity.getIpAddress()),
                new AccessLogUserAgent(jpaEntity.getUserAgent()),
                new AccessLogURI(jpaEntity.getUri()),
                jpaEntity.getHttpMethod(),
                new AccessLogQueryParam(jpaEntity.getQueryParams()),
                new AccessLogRequestBody(jpaEntity.getRequestBody()),
                new AccessLogStatusCode(jpaEntity.getStatusCode()),
                new AccessLogResponseTimeMs(jpaEntity.getResponseTimeMs()),
                new AccessLogErrorMessage(jpaEntity.getErrorMessage()),
                jpaEntity.getCreatedAt()
        );
    }

    public static AccessLogJpaEntity toJpaEntity(AccessLog accessLog) {
        AccessLogJpaEntity jpaEntity = new AccessLogJpaEntity();
        if (accessLog.getId() != null) {
            jpaEntity.setId(accessLog.getId().value());
        }
        jpaEntity.setUser(UserMapper.toJpaEntity(accessLog.getUser()));
        jpaEntity.setIpAddress(accessLog.getIpAddress().value());
        jpaEntity.setUserAgent(accessLog.getUserAgent().value());
        jpaEntity.setUri(accessLog.getUri().value());
        jpaEntity.setHttpMethod(accessLog.getHttpMethod());
        jpaEntity.setQueryParams(accessLog.getQueryParam().value());
        jpaEntity.setRequestBody(accessLog.getRequestBody().value());
        jpaEntity.setResponseTimeMs(accessLog.getResponseTimeMs().value());
        jpaEntity.setStatusCode(accessLog.getStatusCode().value());
        jpaEntity.setErrorMessage(accessLog.getErrorMessage().value());
        jpaEntity.setCreatedAt(accessLog.getCreatedAt());
        return jpaEntity;
    }

}
