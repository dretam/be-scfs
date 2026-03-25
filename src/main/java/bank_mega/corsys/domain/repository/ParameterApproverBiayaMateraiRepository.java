package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMaterai;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiCode;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ParameterApproverBiayaMateraiRepository {

    ParameterApproverBiayaMaterai save(ParameterApproverBiayaMaterai parameter);

    void delete(ParameterApproverBiayaMaterai parameter);

    long count();

    Page<@NonNull ParameterApproverBiayaMaterai> findAllPageable(int page, int size, String sort, String filter);

    Optional<ParameterApproverBiayaMaterai> findFirstById(ParameterApproverBiayaMateraiId id);

    Optional<ParameterApproverBiayaMaterai> findFirstByIdAndAuditDeletedAtIsNull(ParameterApproverBiayaMateraiId id);

    Optional<ParameterApproverBiayaMaterai> findFirstByCode(ParameterApproverBiayaMateraiCode code);

}
