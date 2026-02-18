package bank_mega.corsys.application.document.command;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SoftDeleteDocumentCommand(

        @NotNull
        Long id

) {
}