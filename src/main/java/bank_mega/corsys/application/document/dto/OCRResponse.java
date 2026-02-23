package bank_mega.corsys.application.document.dto;

import lombok.Builder;

import java.time.Instant;

@Builder
public record OCRResponse(
        Long id,
        String atasNama,
        String nominal,
        String jangkaWaktu,
        String periode,
        String rate,
        String alokasi,
        String namaRekeningTujuanPencairan,
        String nomorRekeningTujuanPencairan,
        String nomorRekeningPengirim,
        String nomorRekeningPlacement,
        Instant createdAt,
        Long createdBy,
        Instant updatedAt,
        Long updatedBy,
        Instant deletedAt,
        Long deletedBy
) {
}