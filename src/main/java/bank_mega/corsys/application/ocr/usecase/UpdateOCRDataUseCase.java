package bank_mega.corsys.application.ocr.usecase;

import bank_mega.corsys.application.assembler.OCRAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.document.dto.OCRResponse;
import bank_mega.corsys.application.ocr.command.UpdateOCRDataCommand;
import bank_mega.corsys.domain.exception.DomainRuleViolationException;
import bank_mega.corsys.domain.model.ocr.OCRData;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.repository.OCRDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class UpdateOCRDataUseCase {

    private final OCRDataRepository ocrDataRepository;

    @Transactional
    public OCRResponse execute(UpdateOCRDataCommand command, User authPrincipal) {
        OCRData ocrData = ocrDataRepository.findFirstByIdAndAuditDeletedAtIsNull(command.id())
                .orElseThrow(() -> new DomainRuleViolationException("OCR Data not found with id: " + command.id()));

        // Update fields if provided
        OCRData updatedData = OCRData.builder()
                .id(ocrData.getId())
                .documentId(command.documentId() != null ? command.documentId() : ocrData.getDocumentId())
                .atasNama(command.atasNama() != null ? command.atasNama() : ocrData.getAtasNama())
                .nominal(command.nominal() != null ? command.nominal() : ocrData.getNominal())
                .jangkaWaktu(command.jangkaWaktu() != null ? command.jangkaWaktu() : ocrData.getJangkaWaktu())
                .periode(command.periode() != null ? command.periode() : ocrData.getPeriode())
                .rate(command.rate() != null ? command.rate() : ocrData.getRate())
                .alokasi(command.alokasi() != null ? command.alokasi() : ocrData.getAlokasi())
                .namaRekeningTujuanPencairan(command.namaRekeningTujuanPencairan() != null ? command.namaRekeningTujuanPencairan() : ocrData.getNamaRekeningTujuanPencairan())
                .nomorRekeningTujuanPencairan(command.nomorRekeningTujuanPencairan() != null ? command.nomorRekeningTujuanPencairan() : ocrData.getNomorRekeningTujuanPencairan())
                .nomorRekeningPengirim(command.nomorRekeningPengirim() != null ? command.nomorRekeningPengirim() : ocrData.getNomorRekeningPengirim())
                .nomorRekeningPlacement(command.nomorRekeningPlacement() != null ? command.nomorRekeningPlacement() : ocrData.getNomorRekeningPlacement())
                .audit(ocrData.getAudit().update(authPrincipal.getId().value()))
                .build();

        OCRData saved = ocrDataRepository.save(updatedData);

        return OCRAssembler.toResponse(saved);
    }
}
