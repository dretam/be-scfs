package bank_mega.corsys.application.document.command;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CreateDocumentCommand(

        @NotBlank
        String filename,

        @NotBlank
        String originalName,

        @NotBlank
        String filePath,

        @NotNull
        Long fileSize,

        @NotBlank
        String mimeType,

        @NotNull
        Long uploadedBy

) {
}