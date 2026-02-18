package bank_mega.corsys.application.document.command;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record CreateDocumentMultipartCommand(

        @NotNull
        MultipartFile file

) {
}