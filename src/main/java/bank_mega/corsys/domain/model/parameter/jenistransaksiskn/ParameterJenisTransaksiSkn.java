package bank_mega.corsys.domain.model.parameter.jenistransaksiskn;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class ParameterJenisTransaksiSkn {

    private final ParameterJenisTransaksiSknId id;
    private ParameterJenisTransaksiSknCode code;
    private ParameterJenisTransaksiSknValue value;
    private AuditTrail audit;

    public ParameterJenisTransaksiSkn(
            ParameterJenisTransaksiSknId id,
            ParameterJenisTransaksiSknCode code,
            ParameterJenisTransaksiSknValue value,
            AuditTrail audit
    ) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.audit = audit;
    }

    public ParameterJenisTransaksiSknId getId() {
        return id;
    }

    public ParameterJenisTransaksiSknCode getCode() {
        return code;
    }

    public ParameterJenisTransaksiSknValue getValue() {
        return value;
    }

    public AuditTrail getAudit() {
        return audit;
    }

    public void changeCode(ParameterJenisTransaksiSknCode code) {
        if (code != null) {
            this.code = code;
        }
    }

    public void changeValue(ParameterJenisTransaksiSknValue value) {
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
