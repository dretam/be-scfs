package bank_mega.corsys.domain.model.ocr;
import bank_mega.corsys.domain.model.common.AuditTrail;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OCRData {
    private Long id;
    private Long documentId;
    private String atasNama;
    private String nominal;
    private String jangkaWaktu;
    private String periode;
    private String rate;
    private String alokasi;
    private String namaRekeningTujuanPencairan;
    private String nomorRekeningTujuanPencairan;
    private String nomorRekeningPengirim;
    private String nomorRekeningPlacement;
    private AuditTrail audit;
}