package com.notification.NotificationService.controller;

import com.notification.NotificationService.service.impl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("send-mail")
public class TestController {
    @Autowired
    private MailServiceImpl mailServiceImpl;

    @GetMapping
    public  void sendMail(){
        mailServiceImpl.sendSimpleEmail("deshkarchinmay42@gmail.com", "Test: Testing Mail Service", "This is a test mail sent from my new Spring boot application");
    }
}
