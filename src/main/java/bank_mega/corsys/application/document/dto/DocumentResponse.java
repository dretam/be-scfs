package bank_mega.corsys.application.document.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record DocumentResponse(
        Long id,
        String filename,
        String originalName,
        String filePath,
        Long fileSize,
        String mimeType,
        Long uploadedBy,
        Long userId,
        Instant createdAt,
        Long createdBy,
        Instant updatedAt,
        Long updatedBy,
        Instant deletedAt,
        Long deletedBy
) {
}