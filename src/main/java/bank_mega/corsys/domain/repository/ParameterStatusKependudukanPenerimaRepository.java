package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerima;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaCode;
import bank_mega.corsys.domain.model.parameter.statuskependudukanpenerima.ParameterStatusKependudukanPenerimaId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ParameterStatusKependudukanPenerimaRepository {

    ParameterStatusKependudukanPenerima save(ParameterStatusKependudukanPenerima parameter);

    void delete(ParameterStatusKependudukanPenerima parameter);

    long count();

    Page<@NonNull ParameterStatusKependudukanPenerima> findAllPageable(int page, int size, String sort, String filter);

    Optional<ParameterStatusKependudukanPenerima> findFirstById(ParameterStatusKependudukanPenerimaId id);

    Optional<ParameterStatusKependudukanPenerima> findFirstByIdAndAuditDeletedAtIsNull(ParameterStatusKependudukanPenerimaId id);

    Optional<ParameterStatusKependudukanPenerima> findFirstByCode(ParameterStatusKependudukanPenerimaCode code);

}
