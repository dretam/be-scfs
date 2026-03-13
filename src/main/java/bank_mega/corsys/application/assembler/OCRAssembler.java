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
                .atasNama(saved.getAtasNama())
                .nominal(saved.getNominal())
                .jangkaWaktu(saved.getJangkaWaktu())
                .periode(saved.getPeriode())
                .rate(saved.getRate())
                .alokasi(saved.getAlokasi())
                .namaRekeningTujuanPencairan(saved.getNamaRekeningTujuanPencairan() != null ? saved.getNamaRekeningTujuanPencairan() : "-")
                .nomorRekeningTujuanPencairan(saved.getNomorRekeningTujuanPencairan() != null ? saved.getNomorRekeningTujuanPencairan() : "-")
                .nomorRekeningPengirim(saved.getNomorRekeningPengirim() != null ? saved.getNomorRekeningPengirim() : "-")
                .nomorRekeningPlacement(saved.getNomorRekeningPlacement() != null ? saved.getNomorRekeningPlacement() : "-")
                .status(saved.getStatus());

        return builder.build();
    }

}