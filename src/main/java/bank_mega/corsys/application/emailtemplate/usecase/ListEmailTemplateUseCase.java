package bank_mega.corsys.application.emailtemplate.usecase;

import bank_mega.corsys.application.assembler.EmailTemplateAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.emailtemplate.dto.EmailTemplateResponse;
import bank_mega.corsys.domain.model.emailtemplate.EmailTemplate;
import bank_mega.corsys.domain.repository.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@UseCase
@RequiredArgsConstructor
public class ListEmailTemplateUseCase {

    private final EmailTemplateRepository emailTemplateRepository;

    @Transactional(readOnly = true)
    public List<EmailTemplateResponse> execute() {
        List<EmailTemplate> templates = emailTemplateRepository.findAll();
        return templates.stream()
                .map(EmailTemplateAssembler::toResponse)
                .toList();
    }
}
