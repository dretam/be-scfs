package bank_mega.corsys.domain.model.community;

import bank_mega.corsys.domain.model.common.AuditTrail;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CommunityPricingTier {
    private final PricingTierId id;
    private PricingTierLogic logic;
    private PricingTierNominal nominal;
    private PricingTierQuantity quantity;
    private AuditTrail audit;

    public CommunityPricingTier(
            PricingTierId id,
            PricingTierLogic logic,
            PricingTierNominal nominal,
            PricingTierQuantity quantity,
            AuditTrail audit
    ) {
        this.id = id;
        this.logic = logic;
        this.nominal = nominal;
        this.quantity = quantity;
        this.audit = audit;
    }

    public void changeLogic(PricingTierLogic logic) {
        if (logic != null) {
            this.logic = logic;
        }
    }

    public void changeNominal(PricingTierNominal nominal) {
        if (nominal != null) {
            this.nominal = nominal;
        }
    }

    public void changeQuantity(PricingTierQuantity quantity) {
        if (quantity != null) {
            this.quantity = quantity;
        }
    }

    public void updateAudit(UUID updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(UUID deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }
}
