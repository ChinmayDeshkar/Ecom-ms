package com.notification.NotificationService.service;

public interface MailService {

    public void sendSimpleEmail(String to, String subject, String text);
}
