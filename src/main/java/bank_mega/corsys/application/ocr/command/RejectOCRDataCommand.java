package bank_mega.corsys.application.ocr.command;

import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record RejectOCRDataCommand(
        @NotEmpty(message = "ids cannot be empty")
        List<Long> ids
) {
}
