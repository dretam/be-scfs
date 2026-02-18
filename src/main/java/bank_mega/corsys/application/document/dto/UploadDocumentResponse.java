package bank_mega.corsys.application.document.dto;

import lombok.Builder;

@Builder
public record UploadDocumentResponse(
        Long id,
        String filename,
        String originalName,
        String filePath,
        Long fileSize,
        String mimeType,
        Boolean success,
        String message
) {
}