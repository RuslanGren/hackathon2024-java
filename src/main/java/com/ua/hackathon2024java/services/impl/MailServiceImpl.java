package com.ua.hackathon2024java.services.impl;

import com.ua.hackathon2024java.entity.Regions;
import com.ua.hackathon2024java.entity.User;
import com.ua.hackathon2024java.services.MailService;
import com.ua.hackathon2024java.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final UserService userService;

    @Override
    public void sendEmail(String email, String subject, String htmlContent) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void sendMailsByRegion(Regions region) {
        userService.findAllUsersByRegion(region)
                .stream()
                .map(User::getEmail)
                .forEach(email -> sendEmail(email, "Надішла нова скарга",
                        "<h3>Вітаю!</h3><p>На вашу зазначену область надійшла нова скарга. " +
                                "Деталі можна переглянути за посиланням: " +
                                "<a href=\"https://localhost:8080/reports\">Переглянути звіт</a></p>"
                ));
    }
}