package bank_mega.corsys.domain.model.parameter.metodepembayaranbunga;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class ParameterMetodePembayaranBunga {

    private final ParameterMetodePembayaranBungaId id;
    private ParameterMetodePembayaranBungaCode code;
    private ParameterMetodePembayaranBungaValue value;
    private AuditTrail audit;

    public ParameterMetodePembayaranBunga(
            ParameterMetodePembayaranBungaId id,
            ParameterMetodePembayaranBungaCode code,
            ParameterMetodePembayaranBungaValue value,
            AuditTrail audit
    ) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.audit = audit;
    }

    public ParameterMetodePembayaranBungaId getId() {
        return id;
    }

    public ParameterMetodePembayaranBungaCode getCode() {
        return code;
    }

    public ParameterMetodePembayaranBungaValue getValue() {
        return value;
    }

    public AuditTrail getAudit() {
        return audit;
    }

    public void changeCode(ParameterMetodePembayaranBungaCode code) {
        if (code != null) {
            this.code = code;
        }
    }

    public void changeValue(ParameterMetodePembayaranBungaValue value) {
        if (value != null) {
            this.value = value;
        }
    }

    public void updateAudit(Long updatedBy) {
        this.audit = this.audit.update(updatedBy);
    }

    public void deleteAudit(Long deletedBy) {
        this.audit = this.audit.delete(deletedBy);
    }

}
