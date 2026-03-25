package bank_mega.corsys.application.parameter.approverbiayamaterai.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.approverbiayamaterai.dto.ParameterApproverBiayaMateraiResponse;
import bank_mega.corsys.domain.exception.ParameterApproverBiayaMateraiNotFoundException;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMaterai;
import bank_mega.corsys.domain.model.parameter.approverbiayamaterai.ParameterApproverBiayaMateraiId;
import bank_mega.corsys.domain.repository.ParameterApproverBiayaMateraiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class PageParameterApproverBiayaMateraiUseCase {

    private final ParameterApproverBiayaMateraiRepository parameterApproverBiayaMateraiRepository;

    @Transactional(readOnly = true)
    public Page<ParameterApproverBiayaMateraiResponse> execute(int page, int perPage, String sort, String filter) {
        Page<ParameterApproverBiayaMaterai> pageable = parameterApproverBiayaMateraiRepository.findAllPageable(page, perPage, sort, filter);

        List<ParameterApproverBiayaMateraiResponse> content = pageable.stream()
                .map(p -> ParameterApproverBiayaMateraiResponse.builder()
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
