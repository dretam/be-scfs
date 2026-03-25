package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgs;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsCode;
import bank_mega.corsys.domain.model.parameter.jenistransaksirtgs.ParameterJenisTransaksiRtgsId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ParameterJenisTransaksiRtgsRepository {

    ParameterJenisTransaksiRtgs save(ParameterJenisTransaksiRtgs parameter);

    void delete(ParameterJenisTransaksiRtgs parameter);

    long count();

    Page<@NonNull ParameterJenisTransaksiRtgs> findAllPageable(int page, int size, String sort, String filter);

    Optional<ParameterJenisTransaksiRtgs> findFirstById(ParameterJenisTransaksiRtgsId id);

    Optional<ParameterJenisTransaksiRtgs> findFirstByIdAndAuditDeletedAtIsNull(ParameterJenisTransaksiRtgsId id);

    Optional<ParameterJenisTransaksiRtgs> findFirstByCode(ParameterJenisTransaksiRtgsCode code);

}
