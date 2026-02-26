package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.AuditTrailEmbeddable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ocr_data")
public class OCRDataJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "atas_nama", columnDefinition = "TEXT")
    private String atasNama;

    @Column(name = "nominal")
    private String nominal;

    @Column(name = "jangka_waktu")
    private String jangkaWaktu;

    @Column(name = "periode")
    private String periode;

    @Column(name = "rate")
    private String rate;

    @Column(name = "alokasi")
    private String alokasi;

    @Column(name = "nama_rekening_tujuan_pencairan", columnDefinition = "TEXT")
    private String namaRekeningTujuanPencairan;

    @Column(name = "nomor_rekening_tujuan_pencairan")
    private String nomorRekeningTujuanPencairan;

    @Column(name = "nomor_rekening_pengirim")
    private String nomorRekeningPengirim;

    @Column(name = "nomor_rekening_placement")
    private String nomorRekeningPlacement;

    @OneToOne
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private DocumentJpaEntity document;

    @Embedded
    private AuditTrailEmbeddable audit;
}