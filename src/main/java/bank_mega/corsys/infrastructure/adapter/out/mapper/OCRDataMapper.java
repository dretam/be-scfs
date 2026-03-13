package bank_mega.corsys.infrastructure.adapter.out.mapper;

import bank_mega.corsys.domain.model.ocr.OCRData;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.OCRDataJpaEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OCRDataMapper {

    public OCRData toDomain(OCRDataJpaEntity entity) {
        if (entity == null) return null;

        return OCRData.builder()
                .id(entity.getId())
                .atasNama(entity.getAtasNama() != null ? entity.getAtasNama() : "-")
                .nominal(entity.getNominal() != null ? entity.getNominal() : "-")
                .jangkaWaktu(entity.getJangkaWaktu() != null ? entity.getJangkaWaktu() : "-")
                .periode(entity.getPeriode() != null ? entity.getPeriode() : "-")
                .rate(entity.getRate() != null ? entity.getRate() : "-")
                .alokasi(entity.getAlokasi() != null ? entity.getAlokasi() : "-")
                .namaRekeningTujuanPencairan(entity.getNamaRekeningTujuanPencairan() != null ? entity.getNamaRekeningTujuanPencairan() : "-")
                .nomorRekeningTujuanPencairan(entity.getNomorRekeningTujuanPencairan() != null ? entity.getNomorRekeningTujuanPencairan() : "-")
                .nomorRekeningPengirim(entity.getNomorRekeningPengirim() != null ? entity.getNomorRekeningPengirim() : "-")
                .nomorRekeningPlacement(entity.getNomorRekeningPlacement() != null ? entity.getNomorRekeningPlacement() : "-")
                .status(entity.getStatus())
                .build();
    }

    public OCRDataJpaEntity toJpaEntity(OCRData domain) {
        if (domain == null) return null;

        return OCRDataJpaEntity.builder()
                .id(domain.getId())
                .atasNama(domain.getAtasNama())
                .nominal(domain.getNominal())
                .jangkaWaktu(domain.getJangkaWaktu())
                .periode(domain.getPeriode())
                .rate(domain.getRate())
                .alokasi(domain.getAlokasi())
                .namaRekeningTujuanPencairan(domain.getNamaRekeningTujuanPencairan())
                .nomorRekeningTujuanPencairan(domain.getNomorRekeningTujuanPencairan())
                .nomorRekeningPengirim(domain.getNomorRekeningPengirim())
                .nomorRekeningPlacement(domain.getNomorRekeningPlacement())
                .status(domain.getStatus())
                .build();
    }
}