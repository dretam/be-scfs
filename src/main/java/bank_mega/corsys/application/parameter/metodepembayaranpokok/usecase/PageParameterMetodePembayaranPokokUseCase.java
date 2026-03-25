package bank_mega.corsys.application.parameter.metodepembayaranpokok.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.parameter.metodepembayaranpokok.dto.ParameterMetodePembayaranPokokResponse;
import bank_mega.corsys.domain.exception.ParameterMetodePembayaranPokokNotFoundException;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokok;
import bank_mega.corsys.domain.model.parameter.metodepembayaranpokok.ParameterMetodePembayaranPokokId;
import bank_mega.corsys.domain.repository.ParameterMetodePembayaranPokokRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class PageParameterMetodePembayaranPokokUseCase {

    private final ParameterMetodePembayaranPokokRepository parameterMetodePembayaranPokokRepository;

    @Transactional(readOnly = true)
    public Page<ParameterMetodePembayaranPokokResponse> execute(int page, int perPage, String sort, String filter) {
        Page<ParameterMetodePembayaranPokok> pageable = parameterMetodePembayaranPokokRepository.findAllPageable(page, perPage, sort, filter);

        List<ParameterMetodePembayaranPokokResponse> content = pageable.stream()
                .map(p -> ParameterMetodePembayaranPokokResponse.builder()
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
