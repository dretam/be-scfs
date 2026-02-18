package bank_mega.corsys.application.document.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.DocumentNotFoundException;
import bank_mega.corsys.domain.model.common.Id;
import bank_mega.corsys.domain.model.document.Document;
import bank_mega.corsys.domain.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


@UseCase
@RequiredArgsConstructor
public class RetrieveDocumentUseCase {

    private final DocumentRepository documentRepository;

    @Transactional(readOnly = true)
    public Document execute(Long id, Set<String> expands) {
        return documentRepository.findFirstByIdAndAuditDeletedAtIsNull(new Id(id), expands).orElseThrow(
                () -> new DocumentNotFoundException(new Id(id))
        );
    }

}