package bank_mega.corsys.application.document.command;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record UpdateDocumentMultipartCommand(

        @NotNull
        Long id,

        @NotNull
        MultipartFile file

) {
}