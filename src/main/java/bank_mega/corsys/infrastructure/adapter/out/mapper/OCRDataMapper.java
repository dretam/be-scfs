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
                .atasNama(entity.getAtasNama())
                .nominal(entity.getNominal())
                .jangkaWaktu(entity.getJangkaWaktu())
                .periode(entity.getPeriode())
                .rate(entity.getRate())
                .alokasi(entity.getAlokasi())
                .namaRekeningTujuanPencairan(entity.getNamaRekeningTujuanPencairan())
                .nomorRekeningTujuanPencairan(entity.getNomorRekeningTujuanPencairan())
                .nomorRekeningPengirim(entity.getNomorRekeningPengirim())
                .nomorRekeningPlacement(entity.getNomorRekeningPlacement())
                .audit(AuditTrailEmbeddableMapper.toDomain(entity.getAudit()))
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
                .audit(AuditTrailEmbeddableMapper.toJpa(domain.getAudit()))
                .build();
    }
}