package com.barani.travel.serviceImpl;

import com.barani.travel.service.EmailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendBookingConfirmation(String to, String subject, String body, byte[] pdf, String fileName) {

        try {

            MimeMessage message = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setFrom("baranibarani732@gmail.com", "Dream Destination Tours");
            helper.setText(body, true);

            helper.addAttachment(fileName, new ByteArrayResource(pdf));

            mailSender.send(message);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}