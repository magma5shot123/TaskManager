package com.example.TaskManager.services;

import com.example.TaskManager.exception.EmailSendFailedException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendConfirmationEmail(String email, String code, String token) {

        log.info("Sending confirmation email to {}", email);

        String link = "http://localhost:8085/api/auth/confirm?token=" + token;

        SimpleMailMessage message = getSimpleMailMessage(email, code, link);

        try {
            mailSender.send(message);
            log.debug("Email confirmation email sent");
            log.info("Confirmation email sent to {}", email);
        } catch (Exception ex) {
            log.error("Error while sending confirmation email to {}: {}", email, ex.getMessage());
            throw new EmailSendFailedException(email, ex);
        }
    }

    @NonNull
    private SimpleMailMessage getSimpleMailMessage(String email, String code, String link) {
        String text = """
                
                Ваш код подтверждения: %s
                
                Или перейдите по ссылке:
                %s
                
                Код действует 10 минут.
                
                """.formatted(code, link);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(email);
        message.setSubject("Подтверждение регистрации на TaskManager");
        message.setText(text);
        return message;
    }

}
