package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.ocr.OCRData;

import java.util.List;
import java.util.Optional;

public interface OCRDataRepository {

    OCRData save(OCRData ocrData);

    Optional<OCRData> findFirstById(Long id);

    void delete(OCRData ocrData);
}
