package bank_mega.corsys.application.document.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.document.Document;
import bank_mega.corsys.domain.repository.DocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Set;

@UseCase
@RequiredArgsConstructor
public class GetDocumentUseCase {

    private final DocumentRepository documentRepository;

    public Page<Document> execute(int page, int size, Set<String> expand, String sort, String filter) {
        return documentRepository.findAllPageable(page, size, expand, sort, filter);
    }
}