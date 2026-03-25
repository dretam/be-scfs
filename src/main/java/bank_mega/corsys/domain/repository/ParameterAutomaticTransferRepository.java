package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransfer;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferCode;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ParameterAutomaticTransferRepository {

    ParameterAutomaticTransfer save(ParameterAutomaticTransfer parameter);

    void delete(ParameterAutomaticTransfer parameter);

    long count();

    Page<@NonNull ParameterAutomaticTransfer> findAllPageable(int page, int size, String sort, String filter);

    Optional<ParameterAutomaticTransfer> findFirstById(ParameterAutomaticTransferId id);

    Optional<ParameterAutomaticTransfer> findFirstByIdAndAuditDeletedAtIsNull(ParameterAutomaticTransferId id);

    Optional<ParameterAutomaticTransfer> findFirstByCode(ParameterAutomaticTransferCode code);

}
