package bank_mega.corsys.domain.model.parameter.jenistransaksirtgs;

import bank_mega.corsys.domain.model.common.AuditTrail;

public class ParameterJenisTransaksiRtgs {

    private final ParameterJenisTransaksiRtgsId id;
    private ParameterJenisTransaksiRtgsCode code;
    private ParameterJenisTransaksiRtgsValue value;
    private AuditTrail audit;

    public ParameterJenisTransaksiRtgs(
            ParameterJenisTransaksiRtgsId id,
            ParameterJenisTransaksiRtgsCode code,
            ParameterJenisTransaksiRtgsValue value,
            AuditTrail audit
    ) {
        this.id = id;
        this.code = code;
        this.value = value;
        this.audit = audit;
    }

    public ParameterJenisTransaksiRtgsId getId() {
        return id;
    }

    public ParameterJenisTransaksiRtgsCode getCode() {
        return code;
    }

    public ParameterJenisTransaksiRtgsValue getValue() {
        return value;
    }

    public AuditTrail getAudit() {
        return audit;
    }

    public void changeCode(ParameterJenisTransaksiRtgsCode code) {
        if (code != null) {
            this.code = code;
        }
    }

    public void changeValue(ParameterJenisTransaksiRtgsValue value) {
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
