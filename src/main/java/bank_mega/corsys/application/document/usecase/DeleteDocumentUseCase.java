package bank_mega.corsys.application.document.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.DocumentNotFoundException;
import bank_mega.corsys.domain.model.common.Id;
import bank_mega.corsys.domain.model.document.Document;
import bank_mega.corsys.domain.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteDocumentUseCase {

    private final DocumentRepository documentRepository;

    @Transactional
    public Id execute(Long id) {
        // Find existing document
        Document existingDocument = documentRepository.findFirstById(new Id(id))
                .orElseThrow(() -> new DocumentNotFoundException(new Id(id)));

        // Delete the document
        documentRepository.delete(existingDocument);

        return new Id(id);
    }
}