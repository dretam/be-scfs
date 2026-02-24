package bank_mega.corsys.application.assembler;

import bank_mega.corsys.application.document.dto.OCRResponse;
import bank_mega.corsys.domain.model.ocr.OCRData;

import java.util.Set;

public class OCRAssembler {

    public static OCRResponse toResponse(OCRData saved) {
        if (saved == null) return null;
        return toResponse(saved, Set.of());
    }

    public static OCRResponse toResponse(OCRData saved, Set<String> expands) {
        if (saved == null) return null;

        OCRResponse.OCRResponseBuilder builder = OCRResponse.builder()
                .id(saved.getId())
                .documentId(saved.getDocumentId())
                .atasNama(saved.getAtasNama())
                .nominal(saved.getNominal())
                .jangkaWaktu(saved.getJangkaWaktu())
                .periode(saved.getPeriode())
                .rate(saved.getRate())
                .alokasi(saved.getAlokasi())
                .namaRekeningTujuanPencairan(saved.getNamaRekeningTujuanPencairan())
                .nomorRekeningTujuanPencairan(saved.getNomorRekeningTujuanPencairan())
                .nomorRekeningPengirim(saved.getNomorRekeningPengirim())
                .nomorRekeningPlacement(saved.getNomorRekeningPlacement())
                .createdAt(saved.getAudit() != null ? saved.getAudit().createdAt() : null)
                .createdBy(saved.getAudit() != null ? saved.getAudit().createdBy() : null)
                .updatedAt(saved.getAudit() != null ? saved.getAudit().updatedAt() : null)
                .updatedBy(saved.getAudit() != null ? saved.getAudit().updatedBy() : null)
                .deletedAt(saved.getAudit() != null ? saved.getAudit().deletedAt() : null)
                .deletedBy(saved.getAudit() != null ? saved.getAudit().deletedBy() : null);

        return builder.build();
    }

}