package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSkn;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknCode;
import bank_mega.corsys.domain.model.parameter.jenistransaksiskn.ParameterJenisTransaksiSknId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ParameterJenisTransaksiSknRepository {

    ParameterJenisTransaksiSkn save(ParameterJenisTransaksiSkn parameter);

    void delete(ParameterJenisTransaksiSkn parameter);

    long count();

    Page<@NonNull ParameterJenisTransaksiSkn> findAllPageable(int page, int size, String sort, String filter);

    Optional<ParameterJenisTransaksiSkn> findFirstById(ParameterJenisTransaksiSknId id);

    Optional<ParameterJenisTransaksiSkn> findFirstByIdAndAuditDeletedAtIsNull(ParameterJenisTransaksiSknId id);

    Optional<ParameterJenisTransaksiSkn> findFirstByCode(ParameterJenisTransaksiSknCode code);

}
