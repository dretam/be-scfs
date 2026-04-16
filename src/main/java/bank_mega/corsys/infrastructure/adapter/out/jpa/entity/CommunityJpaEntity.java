package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

import bank_mega.corsys.domain.model.community.*;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.AuditTrailEmbeddable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "communities")
public class CommunityJpaEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(columnDefinition = "text")
    private String description;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "product_type", nullable = false)
    private ProductType productType;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "recourse", nullable = false)
    private Recourse recourse;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "holiday_treatment", nullable = false)
    private HolidayTreatment holidayTreatment;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "transaction_fee_type", nullable = false)
    private TransactionFeeType transactionFeeType;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "interest_type", nullable = false)
    private InterestType interestType;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "interest_basis", nullable = false)
    private InterestBasis interestBasis;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_interest_reserve", nullable = false)
    private Boolean isInterestReserve;

    @Column(name = "is_fund_reserve", nullable = false)
    private Boolean isFundReserve;

    @Column(name = "is_sharing_income", nullable = false)
    private Boolean isSharingIncome;

    @Column(name = "is_extend_financing", nullable = false)
    private Boolean isExtendFinancing;

    @Column(name = "transaction_fee", nullable = false)
    private BigDecimal transactionFee;

    @Column(name = "min_financing", nullable = false)
    private Integer minFinancing;

    @Column(name = "max_financing", nullable = false)
    private Integer maxFinancing;

    @Column(name = "min_tenor", nullable = false)
    private Integer minTenor;

    @Column(name = "max_tenor", nullable = false)
    private Integer maxTenor;

    @Column(name = "grace_period", nullable = false)
    private Integer gracePeriod;

    @Column(name = "penalty_rate", nullable = false)
    private Integer penaltyRate;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(name = "early_payment_fee_type", nullable = false)
    private EarlyPaymentFeeType earlyPaymentFeeType;

    @Column(name = "early_payment_fee_amount", nullable = false)
    private Long earlyPaymentFeeAmount;

    @Embedded
    private AuditTrailEmbeddable audit;

    @OneToMany(mappedBy = "community", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<CommunityPricingTierJpaEntity> pricingTiers = new HashSet<>();
}
