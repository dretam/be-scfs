package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.document.Document;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.DocumentJpaEntity;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;
import java.util.Set;

public class DocumentMapper {

    public static Document toDomain(@NotNull DocumentJpaEntity jpaEntity) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");
        return Document.builder()
                .id(jpaEntity.getId())
                .filename(jpaEntity.getFilename())
                .originalName(jpaEntity.getOriginalName())
                .filePath(jpaEntity.getFilePath())
                .fileSize(jpaEntity.getFileSize())
                .mimeType(jpaEntity.getMimeType())
                .uploadedBy(jpaEntity.getAudit().getCreatedBy())
                .audit(AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit()))
                .build();
    }

    public static Document toDomain(@NotNull DocumentJpaEntity jpaEntity, Set<String> expands) {
        if (jpaEntity == null) throw new DomainRuleViolationException("JPA Entity is null");
        return Document.builder()
                .id(jpaEntity.getId())
                .filename(jpaEntity.getFilename())
                .originalName(jpaEntity.getOriginalName())
                .filePath(jpaEntity.getFilePath())
                .fileSize(jpaEntity.getFileSize())
                .mimeType(jpaEntity.getMimeType())
                .uploadedBy(jpaEntity.getAudit().getCreatedBy())
                .audit(AuditTrailEmbeddableMapper.toDomain(jpaEntity.getAudit()))
                .build();
    }

    public static DocumentJpaEntity toJpaEntity(Document document) {
        DocumentJpaEntity jpaEntity = new DocumentJpaEntity();
        if (document.getId() != null) {
            jpaEntity.setId(document.getId());
        }
        jpaEntity.setFilename(document.getFilename());
        jpaEntity.setOriginalName(document.getOriginalName());
        jpaEntity.setFilePath(document.getFilePath());
        jpaEntity.setFileSize(document.getFileSize());
        jpaEntity.setMimeType(document.getMimeType());
        jpaEntity.setAudit(AuditTrailEmbeddableMapper.toJpa(document.getAudit()));
        return jpaEntity;
    }

}