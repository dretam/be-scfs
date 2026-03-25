package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBunga;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaCode;
import bank_mega.corsys.domain.model.parameter.metodepembayaranbunga.ParameterMetodePembayaranBungaId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ParameterMetodePembayaranBungaRepository {

    ParameterMetodePembayaranBunga save(ParameterMetodePembayaranBunga parameter);

    void delete(ParameterMetodePembayaranBunga parameter);

    long count();

    Page<@NonNull ParameterMetodePembayaranBunga> findAllPageable(int page, int size, String sort, String filter);

    Optional<ParameterMetodePembayaranBunga> findFirstById(ParameterMetodePembayaranBungaId id);

    Optional<ParameterMetodePembayaranBunga> findFirstByIdAndAuditDeletedAtIsNull(ParameterMetodePembayaranBungaId id);

    Optional<ParameterMetodePembayaranBunga> findFirstByCode(ParameterMetodePembayaranBungaCode code);

}
