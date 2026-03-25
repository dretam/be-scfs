package bank_mega.corsys.application.parameter.jenistransfer.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.jenistransfer.dto.ParameterJenisTransferResponse;
import bank_mega.corsys.domain.exception.ParameterJenisTransferNotFoundException;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransfer;
import bank_mega.corsys.domain.model.parameter.jenistransfer.ParameterJenisTransferId;
import bank_mega.corsys.domain.repository.ParameterJenisTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class PageParameterJenisTransferUseCase {

    private final ParameterJenisTransferRepository parameterJenisTransferRepository;

    @Transactional(readOnly = true)
    public Page<ParameterJenisTransferResponse> execute(int page, int perPage, String sort, String filter) {
        Page<ParameterJenisTransfer> pageable = parameterJenisTransferRepository.findAllPageable(page, perPage, sort, filter);

        List<ParameterJenisTransferResponse> content = pageable.stream()
                .map(p -> ParameterJenisTransferResponse.builder()
                        .code(p.getCode().value())
                        .value(p.getValue().value())
                        .build())
                .toList();

        return new PageImpl<>(
                content,
                PageRequest.of(page - 1, perPage),
                pageable.getTotalElements()
        );
    }

}
