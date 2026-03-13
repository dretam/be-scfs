package bank_mega.corsys.application.ocr.usecase;

import bank_mega.corsys.application.assembler.OCRAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.document.dto.OCRResponse;
import bank_mega.corsys.application.ocr.command.RejectOCRDataCommand;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.ocr.OCRData;
import bank_mega.corsys.domain.model.ocr.OCRStatus;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.OCRDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@UseCase
@RequiredArgsConstructor
public class RejectOCRDataUseCase {

    private final OCRDataRepository ocrDataRepository;

    @Transactional
    public List<OCRResponse> execute(RejectOCRDataCommand command, User authPrincipal) {
        List<OCRResponse> responses = new ArrayList<>();

        for (Long id : command.ids()) {
            OCRData ocrData = ocrDataRepository.findFirstByIdAndAuditDeletedAtIsNull(id)
                    .orElseThrow(() -> new DomainRuleViolationException("OCR Data not found with id: " + id));

            OCRData rejectedData = OCRData.builder()
                    .id(ocrData.getId())
                    .atasNama(ocrData.getAtasNama())
                    .nominal(ocrData.getNominal())
                    .jangkaWaktu(ocrData.getJangkaWaktu())
                    .periode(ocrData.getPeriode())
                    .rate(ocrData.getRate())
                    .alokasi(ocrData.getAlokasi())
                    .namaRekeningTujuanPencairan(ocrData.getNamaRekeningTujuanPencairan())
                    .nomorRekeningTujuanPencairan(ocrData.getNomorRekeningTujuanPencairan())
                    .nomorRekeningPengirim(ocrData.getNomorRekeningPengirim())
                    .nomorRekeningPlacement(ocrData.getNomorRekeningPlacement())
                    .status(OCRStatus.REJECTED)
                    .build();

            OCRData saved = ocrDataRepository.save(rejectedData);
            responses.add(OCRAssembler.toResponse(saved));
        }

        return responses;
    }
}
