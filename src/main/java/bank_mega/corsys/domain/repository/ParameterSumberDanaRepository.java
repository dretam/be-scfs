package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDana;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaCode;
import bank_mega.corsys.domain.model.parameter.sumberdana.ParameterSumberDanaId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ParameterSumberDanaRepository {

    ParameterSumberDana save(ParameterSumberDana parameter);

    void delete(ParameterSumberDana parameter);

    long count();

    Page<@NonNull ParameterSumberDana> findAllPageable(int page, int size, String sort, String filter);

    Optional<ParameterSumberDana> findFirstById(ParameterSumberDanaId id);

    Optional<ParameterSumberDana> findFirstByIdAndAuditDeletedAtIsNull(ParameterSumberDanaId id);

    Optional<ParameterSumberDana> findFirstByCode(ParameterSumberDanaCode code);

}
