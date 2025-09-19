package com.notification.NotificationService.service.impl;

import com.notification.NotificationService.service.MailService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleEmail(String to, String subject, String text) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");


            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
//            SimpleMailMessage message = new SimpleMailMessage();
//            message.setTo(to);
//            message.setSubject(subject);
//            message.setText(text);
//            System.out.println("------------------------------------------------------------------------------------");
//            System.out.println(text);
//            message.setFrom("itschinmayd@gmail.com");
            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        }

}
