package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.ocr.OCRData;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface OCRDataRepository {

    OCRData save(OCRData ocrData);

    List<OCRData> saveAll(List<OCRData> ocrDataList);

    Optional<OCRData> findFirstById(Long id);

    Optional<OCRData> findFirstByIdAndAuditDeletedAtIsNull(Long id);

    Page<OCRData> findAllPageable(
            int page,
            int size,
            Set<String> expands,
            String sort,
            String filter
    );

    void delete(OCRData ocrData);
}
