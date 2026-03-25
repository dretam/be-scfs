package bank_mega.corsys.application.parameter.automatictransfer.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.automatictransfer.dto.ParameterAutomaticTransferResponse;
import bank_mega.corsys.domain.exception.ParameterAutomaticTransferNotFoundException;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransfer;
import bank_mega.corsys.domain.model.parameter.automatictransfer.ParameterAutomaticTransferId;
import bank_mega.corsys.domain.repository.ParameterAutomaticTransferRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class PageParameterAutomaticTransferUseCase {

    private final ParameterAutomaticTransferRepository parameterAutomaticTransferRepository;

    @Transactional(readOnly = true)
    public Page<ParameterAutomaticTransferResponse> execute(int page, int perPage, String sort, String filter) {
        Page<ParameterAutomaticTransfer> pageable = parameterAutomaticTransferRepository.findAllPageable(page, perPage, sort, filter);

        List<ParameterAutomaticTransferResponse> content = pageable.stream()
                .map(p -> ParameterAutomaticTransferResponse.builder()
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
