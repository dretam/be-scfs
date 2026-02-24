package bank_mega.corsys.application.ocr.usecase;

import bank_mega.corsys.application.assembler.OCRAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.document.dto.OCRResponse;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.ocr.OCRData;
import bank_mega.corsys.domain.repository.OCRDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@UseCase
@RequiredArgsConstructor
public class RetrieveOCRDataUseCase {

    private final OCRDataRepository ocrDataRepository;

    @Transactional(readOnly = true)
    public OCRResponse execute(Long id, Set<String> expands) {
        OCRData ocrData = ocrDataRepository.findFirstByIdAndAuditDeletedAtIsNull(id)
                .orElseThrow(() -> new DomainRuleViolationException("OCR Data not found with id: " + id));

        return OCRAssembler.toResponse(ocrData, expands);
    }

    @Transactional(readOnly = true)
    public OCRResponse execute(Long id) {
        return execute(id, Set.of());
    }
}
