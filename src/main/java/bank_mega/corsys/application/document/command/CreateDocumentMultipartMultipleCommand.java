package bank_mega.corsys.application.document.command;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Builder
public record CreateDocumentMultipartMultipleCommand(
        List<MultipartFile> files
) {
}

