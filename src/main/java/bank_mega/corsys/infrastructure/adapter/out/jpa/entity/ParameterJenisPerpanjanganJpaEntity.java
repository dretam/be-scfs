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
@Table(name = "parameter_jenis_perpanjangan")
public class ParameterJenisPerpanjanganJpaEntity {

    @Id
    @Column(length = 1)
    private String code;

    @Column(nullable = false, length = 50)
    private String value;

    @Embedded
    private AuditTrailEmbeddable audit;

}
