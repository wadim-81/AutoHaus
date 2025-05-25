package org.workingproject45efs.service.mail;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class TemplateProjectMailSender {

    private final JavaMailSender mailSender;

    public TemplateProjectMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendConfirmationEmail(String recipientEmail, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Подтверждение регистрации");
        message.setText("Для подтверждения регистрации используйте код: " + code +
                        "\nИли перейдите по ссылке: http://localhost:8080/api/auth/confirm?code=" + code);
        mailSender.send(message);
    }
}
