package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.emailtemplate.*;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.EmailTemplateJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.EmailTemplateSpecificEmailJpaEntity;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.EmailTemplateVariableJpaEntity;
import jakarta.validation.constraints.NotNull;

import java.util.Set;
import java.util.stream.Collectors;

public class EmailTemplateMapper {

    public static EmailTemplate toDomain(@NotNull EmailTemplateJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null (EmailTemplateMapper)");

        Set<EmailTemplateVariable> domainVariables = toDomainVariables(jpaEntity.getVariables());
        Set<EmailTemplateSpecificEmail> domainSpecificEmails = toDomainSpecificEmails(jpaEntity.getSpecificEmails());

        return new EmailTemplate(
                new EmailTemplateId(jpaEntity.getId()),
                jpaEntity.getVariant(),
                new EmailTemplateSubject(jpaEntity.getSubject()),
                new EmailTemplateBody(jpaEntity.getBody()),
                new EmailTemplateIsActive(jpaEntity.getIsActive()),
                new EmailTemplateIsSendToAnchor(jpaEntity.getIsSendToAnchor()),
                new EmailTemplateIsSendToSupplier(jpaEntity.getIsSendToSupplier()),
                new EmailTemplateIsSendToAdminBank(jpaEntity.getIsSendToAdminBank()),
                new EmailTemplateCcIsSendToRm(jpaEntity.getCcIsSendToRm()),
                new EmailTemplateCcIsSendToChecker(jpaEntity.getCcIsSendToChecker()),
                new EmailTemplateCcIsSendToSigner(jpaEntity.getCcIsSendToSigner()),
                new EmailTemplateCcIsSendToSpecifiedMail(jpaEntity.getCcIsSendToSpecifiedMail()),
                domainVariables,
                domainSpecificEmails,
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    public static EmailTemplateJpaEntity toJpaEntity(EmailTemplate domainEntity) {
        EmailTemplateJpaEntity jpaEntity = new EmailTemplateJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setId(domainEntity.getId().value());
        }
        jpaEntity.setVariant(domainEntity.getVariant());
        jpaEntity.setSubject(domainEntity.getSubject().value());
        jpaEntity.setBody(domainEntity.getBody().value());
        jpaEntity.setIsActive(domainEntity.getIsActive().value());
        jpaEntity.setIsSendToAnchor(domainEntity.getIsSendToAnchor().value());
        jpaEntity.setIsSendToSupplier(domainEntity.getIsSendToSupplier().value());
        jpaEntity.setIsSendToAdminBank(domainEntity.getIsSendToAdminBank().value());
        jpaEntity.setCcIsSendToRm(domainEntity.getCcIsSendToRm().value());
        jpaEntity.setCcIsSendToChecker(domainEntity.getCcIsSendToChecker().value());
        jpaEntity.setCcIsSendToSigner(domainEntity.getCcIsSendToSigner().value());
        jpaEntity.setCcIsSendToSpecifiedMail(domainEntity.getCcIsSendToSpecifiedMail().value());

        Set<EmailTemplateVariableJpaEntity> variables = toJpaVariables(domainEntity.getVariables());
        variables.forEach(var -> var.setEmailTemplate(jpaEntity));
        jpaEntity.setVariables(variables);

        Set<EmailTemplateSpecificEmailJpaEntity> specificEmails = toJpaSpecificEmails(domainEntity.getSpecificEmails());
        specificEmails.forEach(email -> email.setEmailTemplate(jpaEntity));
        jpaEntity.setSpecificEmails(specificEmails);

        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

    private static Set<EmailTemplateVariable> toDomainVariables(Set<EmailTemplateVariableJpaEntity> jpaEntities) {
        if (jpaEntities == null) return Set.of();
        return jpaEntities.stream()
                .map(EmailTemplateMapper::toDomainVariable)
                .collect(Collectors.toSet());
    }

    private static Set<EmailTemplateVariableJpaEntity> toJpaVariables(Set<EmailTemplateVariable> domainEntities) {
        if (domainEntities == null) return Set.of();
        return domainEntities.stream()
                .map(EmailTemplateMapper::toJpaVariable)
                .collect(Collectors.toSet());
    }

    private static EmailTemplateVariable toDomainVariable(EmailTemplateVariableJpaEntity jpaEntity) {
        return new EmailTemplateVariable(
                new EmailTemplateVariableId(jpaEntity.getId()),
                new EmailTemplateVariableFlag(jpaEntity.getFlag()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    private static EmailTemplateVariableJpaEntity toJpaVariable(EmailTemplateVariable domainEntity) {
        EmailTemplateVariableJpaEntity jpaEntity = new EmailTemplateVariableJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setId(domainEntity.getId().value());
        }
        jpaEntity.setFlag(domainEntity.getFlag().value());
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }

    private static Set<EmailTemplateSpecificEmail> toDomainSpecificEmails(Set<EmailTemplateSpecificEmailJpaEntity> jpaEntities) {
        if (jpaEntities == null) return Set.of();
        return jpaEntities.stream()
                .map(EmailTemplateMapper::toDomainSpecificEmail)
                .collect(Collectors.toSet());
    }

    private static Set<EmailTemplateSpecificEmailJpaEntity> toJpaSpecificEmails(Set<EmailTemplateSpecificEmail> domainEntities) {
        if (domainEntities == null) return Set.of();
        return domainEntities.stream()
                .map(EmailTemplateMapper::toJpaSpecificEmail)
                .collect(Collectors.toSet());
    }

    private static EmailTemplateSpecificEmail toDomainSpecificEmail(EmailTemplateSpecificEmailJpaEntity jpaEntity) {
        return new EmailTemplateSpecificEmail(
                new EmailTemplateSpecificEmailId(jpaEntity.getId()),
                new EmailTemplateSpecificEmailUserId(jpaEntity.getUserId()),
                new EmailTemplateSpecificEmailAddress(jpaEntity.getEmail()),
                AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit())
        );
    }

    private static EmailTemplateSpecificEmailJpaEntity toJpaSpecificEmail(EmailTemplateSpecificEmail domainEntity) {
        EmailTemplateSpecificEmailJpaEntity jpaEntity = new EmailTemplateSpecificEmailJpaEntity();
        if (domainEntity.getId() != null) {
            jpaEntity.setId(domainEntity.getId().value());
        }
        jpaEntity.setUserId(domainEntity.getUserId().value());
        jpaEntity.setEmail(domainEntity.getEmailAddress().value());
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(domainEntity.getAudit()));
        return jpaEntity;
    }
}
