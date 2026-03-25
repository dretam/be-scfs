package bank_mega.corsys.domain.repository;

import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokok;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokCode;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokId;
import lombok.NonNull;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ParameterMetodePembayaranPokokRepository {

    ParameterMetodePembayaranPokok save(ParameterMetodePembayaranPokok parameter);

    void delete(ParameterMetodePembayaranPokok parameter);

    long count();

    Page<@NonNull ParameterMetodePembayaranPokok> findAllPageable(int page, int size, String sort, String filter);

    Optional<ParameterMetodePembayaranPokok> findFirstById(ParameterMetodePembayaranPokokId id);

    Optional<ParameterMetodePembayaranPokok> findFirstByIdAndAuditDeletedAtIsNull(ParameterMetodePembayaranPokokId id);

    Optional<ParameterMetodePembayaranPokok> findFirstByCode(ParameterMetodePembayaranPokokCode code);

}
