package bank_mega.corsys.domain.model.parameter.sumberdana;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class ParameterSumberDana {

    private final ParameterSumberDanaId id;
    private ParameterSumberDanaCode code;
    private ParameterSumberDanaValue value;
    private AuditTrail audit;

    public ParameterSumberDana(
            ParameterSumberDanaId id,
            ParameterSumberDanaCode code,
            ParameterSumberDanaValue value,
            AuditTrail audit
    ) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.audit = audit;
    }

    public ParameterSumberDanaId getId() {
        return id;
    }

    public ParameterSumberDanaCode getCode() {
        return code;
    }

    public ParameterSumberDanaValue getValue() {
        return value;
    }

    public AuditTrail getAudit() {
        return audit;
    }

    public void changeCode(ParameterSumberDanaCode code) {
        if (code != null) {
            this.code = code;
        }
    }

    public void changeValue(ParameterSumberDanaValue value) {
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
