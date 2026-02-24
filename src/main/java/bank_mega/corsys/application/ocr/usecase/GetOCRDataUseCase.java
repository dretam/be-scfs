package bank_mega.corsys.application.ocr.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.domain.model.ocr.OCRData;
import bank_mega.corsys.domain.repository.OCRDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.Set;

@UseCase
@RequiredArgsConstructor
public class GetOCRDataUseCase {

    private final OCRDataRepository ocrDataRepository;

    public Page<OCRData> execute(int page, int size, Set<String> expand, String sort, String filter) {
        return ocrDataRepository.findAllPageable(page, size, expand, sort, filter);
    }
}
