package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransfer;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferCode;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ParameterJenisTransferRepository {

    ParameterJenisTransfer save(ParameterJenisTransfer parameter);

    void delete(ParameterJenisTransfer parameter);

    long count();

    Page<@NonNull ParameterJenisTransfer> findAllPageable(int page, int size, String sort, String filter);

    Optional<ParameterJenisTransfer> findFirstById(ParameterJenisTransferId id);

    Optional<ParameterJenisTransfer> findFirstByIdAndAuditDeletedAtIsNull(ParameterJenisTransferId id);

    Optional<ParameterJenisTransfer> findFirstByCode(ParameterJenisTransferCode code);

}
