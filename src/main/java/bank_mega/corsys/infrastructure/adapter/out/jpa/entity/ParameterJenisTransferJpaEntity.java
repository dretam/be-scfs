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
@Table(name = "parameter_jenis_transfer")
public class ParameterJenisTransferJpaEntity {

    @Id
    private Integer code;

    @Column(nullable = false, length = 50)
    private String value;

    @Embedded
    private AuditTrailEmbeddable audit;

}
