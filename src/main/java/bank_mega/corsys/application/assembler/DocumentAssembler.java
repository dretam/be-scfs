package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.document.dto.DocumentResponse;
import bank_mega.corsys.application.document.dto.UploadDocumentResponse;
import bank_mega.corsys.domain.model.document.Document;

import java.util.Set;

public class DocumentAssembler {

    public static DocumentResponse toResponse(Document saved) {
        if (saved == null) return null;
        return toResponse(saved, Set.of());
    }

    public static DocumentResponse toResponse(Document saved, Set<String> expands) {
        if (saved == null) return null;
        DocumentResponse.DocumentResponseBuilder builder = DocumentResponse.builder()
                .id(saved.getId())
                .filename(saved.getFilename())
                .originalName(saved.getOriginalName())
                .filePath(saved.getFilePath())
                .fileSize(saved.getFileSize())
                .mimeType(saved.getMimeType())
                .uploadedBy(saved.getUploadedBy())
                .userId(saved.getUserId())
                .createdAt(saved.getAudit() != null ? saved.getAudit().createdAt() : null)
                .createdBy(saved.getAudit() != null ? saved.getAudit().createdBy() : null)
                .updatedAt(saved.getAudit() != null ? saved.getAudit().updatedAt() : null)
                .updatedBy(saved.getAudit() != null ? saved.getAudit().updatedBy() : null)
                .deletedAt(saved.getAudit() != null ? saved.getAudit().deletedAt() : null)
                .deletedBy(saved.getAudit() != null ? saved.getAudit().deletedBy() : null);

        return builder.build();
    }
}