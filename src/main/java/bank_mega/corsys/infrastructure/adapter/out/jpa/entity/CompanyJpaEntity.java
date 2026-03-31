package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

import bank_mega.corsys.domain.model.company.CompanyType;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.AuditTrailEmbeddable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "companies")
public class CompanyJpaEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String cif;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "type_company", nullable = false)
    private CompanyType type;

    @Column(name = "rm_user_id", nullable = true)
    private UUID rmUserId;

    @Column(name = "discount_rate", nullable = false)
    private Integer discountRate;

    @Column(name = "max_financing", nullable = false)
    private Integer maxFinancing;

    @Embedded
    private AuditTrailEmbeddable audit;
}
