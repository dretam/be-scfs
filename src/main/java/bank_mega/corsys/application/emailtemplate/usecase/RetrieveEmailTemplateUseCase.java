package bank_mega.corsys.application.emailtemplate.usecase;

import bank_mega.corsys.application.assembler.EmailTemplateAssembler;
import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.emailtemplate.dto.EmailTemplateResponse;
import bank_mega.corsys.domain.exception.EmailTemplateNotFoundException;
import bank_mega.corsys.domain.model.emailtemplate.EmailTemplate;
import bank_mega.corsys.domain.model.emailtemplate.EmailTemplateVariant;
import bank_mega.corsys.domain.repository.EmailTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@RequiredArgsConstructor
public class RetrieveEmailTemplateUseCase {

    private final EmailTemplateRepository emailTemplateRepository;

    @Transactional(readOnly = true)
    public EmailTemplateResponse execute(EmailTemplateVariant variant) {
        EmailTemplate template = emailTemplateRepository.findByVariant(variant)
                .orElseThrow(() -> new EmailTemplateNotFoundException(variant));
        
        return EmailTemplateAssembler.toResponse(template);
    }
}
