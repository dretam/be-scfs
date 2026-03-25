package bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class ParameterStatusKependudukanPenerima {

    private final ParameterStatusKependudukanPenerimaId id;
    private ParameterStatusKependudukanPenerimaCode code;
    private ParameterStatusKependudukanPenerimaValue value;
    private AuditTrail audit;

    public ParameterStatusKependudukanPenerima(
            ParameterStatusKependudukanPenerimaId id,
            ParameterStatusKependudukanPenerimaCode code,
            ParameterStatusKependudukanPenerimaValue value,
            AuditTrail audit
    ) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.audit = audit;
    }

    public ParameterStatusKependudukanPenerimaId getId() {
        return id;
    }

    public ParameterStatusKependudukanPenerimaCode getCode() {
        return code;
    }

    public ParameterStatusKependudukanPenerimaValue getValue() {
        return value;
    }

    public AuditTrail getAudit() {
        return audit;
    }

    public void changeCode(ParameterStatusKependudukanPenerimaCode code) {
        if (code != null) {
            this.code = code;
        }
    }

    public void changeValue(ParameterStatusKependudukanPenerimaValue value) {
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
