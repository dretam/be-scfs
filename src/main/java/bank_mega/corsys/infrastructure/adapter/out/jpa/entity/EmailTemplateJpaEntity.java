package bank_mega.corsys.infrastructure.adapter.out.jpa.entity;

import bank_mega.corsys.domain.model.emailtemplate.EmailTemplateVariant;
import bank_mega.corsys.infrastructure.adapter.out.jpa.entity.embeddable.AuditTrailEmbeddable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "email_templates")
public class EmailTemplateJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    @Column(nullable = false)
    private EmailTemplateVariant variant;

    @Column(nullable = false, columnDefinition = "text")
    private String subject;

    @Column(columnDefinition = "text")
    private String body;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_send_to_anchor", nullable = false)
    private Boolean isSendToAnchor;

    @Column(name = "is_send_to_supplier", nullable = false)
    private Boolean isSendToSupplier;

    @Column(name = "is_send_to_admin_bank", nullable = false)
    private Boolean isSendToAdminBank;

    @Column(name = "cc_is_send_to_rm", nullable = false)
    private Boolean ccIsSendToRm;

    @Column(name = "cc_is_send_to_checker", nullable = false)
    private Boolean ccIsSendToChecker;

    @Column(name = "cc_is_send_to_signer", nullable = false)
    private Boolean ccIsSendToSigner;

    @Column(name = "cc_is_send_to_specified_mail", nullable = false)
    private Boolean ccIsSendToSpecifiedMail;

    @OneToMany(mappedBy = "emailTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private Set<EmailTemplateVariableJpaEntity> variables = new HashSet<>();

    @OneToMany(mappedBy = "emailTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @Builder.Default
    private Set<EmailTemplateSpecificEmailJpaEntity> specificEmails = new HashSet<>();

    @Embedded
    private AuditTrailEmbeddable audit;
}
