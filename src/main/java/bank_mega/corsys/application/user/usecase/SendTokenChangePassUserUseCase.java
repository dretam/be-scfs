package bank_mega.corsys.application.user.usecase;

import bank_mega.corsys.application.common.annotation.UseCase;
import bank_mega.corsys.application.user.command.SendTokenChangePassUserCommand;
import bank_mega.corsys.domain.model.common.AuditTrail;
import bank_mega.corsys.domain.model.forgotpasstoken.ForgotToken;
import bank_mega.corsys.domain.model.forgotpasstoken.ForgotTokenHash;
import bank_mega.corsys.domain.model.forgotpasstoken.ForgotTokenUsed;
import bank_mega.corsys.domain.model.user.User;
import bank_mega.corsys.domain.model.user.UserEmail;
import bank_mega.corsys.domain.repository.ForgotTokenRepository;
import bank_mega.corsys.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@UseCase
@RequiredArgsConstructor
public class SendTokenChangePassUserUseCase {

    private final JavaMailSender mailSender;

    private final UserRepository userRepository;

    private final ForgotTokenRepository forgotTokenRepository;

    @Value("tamaskywalker2@gmail.com")
    private String fromEmail;

    public Boolean sendTokenChangePassUserViaEmail(SendTokenChangePassUserCommand command) {
        try{
            userRepository
                .findFirstByEmailAndAuditDeletedAtIsNull(new UserEmail(command.email()))
                .ifPresent(user -> {
                    String subject = "SCFS Token Change Password";

                    String generateToken = generateForgotToken();

                    Instant forgotTokenExpiresAt = Instant.now().plus(15, ChronoUnit.MINUTES);

                    ForgotToken forgotToken = new ForgotToken(
                        null,
                        user,
                        new ForgotTokenHash(generateToken),
                        forgotTokenExpiresAt,
                        new ForgotTokenUsed(false),
                        AuditTrail.create(user.getId().value())
                    );

                    forgotTokenRepository.save(forgotToken);

                    // dev
                    // String text = "Link Change Password: https://fescfs.bankmegadev.local/changePass?token=" +
                    // generateToken;

                    // local
                    String text = "Link Change Password: http://localhost:3000/changePass?token=" + generateToken;

                    SimpleMailMessage message = new SimpleMailMessage();
                    message.setFrom(fromEmail);
                    message.setTo(command.email());
                    message.setSubject(subject);
                    message.setText(text);

                    mailSender.send(message);
                });

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String generateForgotToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}
