package bank_mega.corsys.application.ocr.command;

import jakarta.validation.constraints.NotNull;

public record UpdateOCRDataCommand(
        @NotNull(message = "id is required")
        Long id,

        Long documentId,

        String atasNama,

        String nominal,

        String jangkaWaktu,

        String periode,

        String rate,

        String alokasi,

        String namaRekeningTujuanPencairan,

        String nomorRekeningTujuanPencairan,

        String nomorRekeningPengirim,

        String nomorRekeningPlacement
) {
}
