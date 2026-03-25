package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjangan;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganCode;
import bank_mega.corsys.domain.model.parameter.jenisperpanjangan.ParameterJenisPerpanjanganId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ParameterJenisPerpanjanganRepository {

    ParameterJenisPerpanjangan save(ParameterJenisPerpanjangan parameter);

    void delete(ParameterJenisPerpanjangan parameter);

    long count();

    Page<@NonNull ParameterJenisPerpanjangan> findAllPageable(int page, int size, String sort, String filter);

    Optional<ParameterJenisPerpanjangan> findFirstById(ParameterJenisPerpanjanganId id);

    Optional<ParameterJenisPerpanjangan> findFirstByIdAndAuditDeletedAtIsNull(ParameterJenisPerpanjanganId id);

    Optional<ParameterJenisPerpanjangan> findFirstByCode(ParameterJenisPerpanjanganCode code);

}
