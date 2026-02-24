package bank_mega.corsys.application.ocr.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.common.Id;
import bank_mega.corsys.domain.model.ocr.OCRData;
import bank_mega.corsys.domain.repository.OCRDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class DeleteOCRDataUseCase {

    private final OCRDataRepository ocrDataRepository;

    @Transactional
    public Id execute(Long id) {
        OCRData ocrData = ocrDataRepository.findFirstById(id)
                .orElseThrow(() -> new DomainRuleViolationException("OCR Data not found with id: " + id));

        ocrDataRepository.delete(ocrData);

        return new Id(id);
    }
}
