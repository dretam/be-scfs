package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerima;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaCode;
import bank_mega.corsys.domain.model.parameter.jenisnasabahpenerima.ParameterJenisNasabahPenerimaId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ParameterJenisNasabahPenerimaRepository {

    ParameterJenisNasabahPenerima save(ParameterJenisNasabahPenerima parameter);

    void delete(ParameterJenisNasabahPenerima parameter);

    long count();

    Page<@NonNull ParameterJenisNasabahPenerima> findAllPageable(int page, int size, String sort, String filter);

    Optional<ParameterJenisNasabahPenerima> findFirstById(ParameterJenisNasabahPenerimaId id);

    Optional<ParameterJenisNasabahPenerima> findFirstByIdAndAuditDeletedAtIsNull(ParameterJenisNasabahPenerimaId id);

    Optional<ParameterJenisNasabahPenerima> findFirstByCode(ParameterJenisNasabahPenerimaCode code);

}
