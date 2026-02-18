package bank_mega.corsys.application.document.command;

import lombok.Builder;

@Builder
public record GetDocumentCommand(
        
        Long id,
        Integer page,
        Integer size,
        String sort,
        String filter
        
) {
}