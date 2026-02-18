package bank_mega.corsys.application.document.usecase;

import bank_mega.corsys.application.assembler.DocumentAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.document.command.SoftDeleteDocumentCommand;
import bank_mega.corsys.application.document.dto.DocumentResponse;
import bank_mega.corsys.domain.exception.DocumentNotFoundException;
import bank_mega.corsys.domain.model.common.Id;
import bank_mega.corsys.domain.model.document.Document;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class SoftDeleteDocumentUseCase {

    private final DocumentRepository documentRepository;

    @Transactional
    public DocumentResponse execute(SoftDeleteDocumentCommand command, User authPrincipal) {

        // 1. Validate that document exists
        Document document = documentRepository.findFirstByIdAndAuditDeletedAtIsNull(new Id(command.id()))
                .orElseThrow(() -> new DocumentNotFoundException(new Id(command.id())));

        // 2. Update Soft Delete
        document.deleteAudit(authPrincipal.getId().value());

        // 3. Save in repository
        Document saved = documentRepository.save(document);

        // 4. Convert to response DTO
        return DocumentAssembler.toResponse(saved);
    }
}