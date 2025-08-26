package com.notification.NotificationService.config;

import com.notification.NotificationService.dto.Customer;
import com.notification.NotificationService.dto.Order;
import com.notification.NotificationService.dto.Topics;
import com.notification.NotificationService.service.CustomerClient;
import com.notification.NotificationService.service.impl.MailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
public class KafkaConsumerConfig {
    @Autowired
    private MailServiceImpl mailServiceImpl;
    @Autowired
    private CustomerClient customerClient;

    @KafkaListener(topics = Topics.orderPlaced, groupId = "group-1")
    public void newOrder(@Payload Order order) {

//        to subject text
        Customer customer = customerClient.getCustomerDetails(order.getCustomerId());
        String to = customer.getEmailId();
        String subject = "Congratulation! Order has been placed!! Order No. " + order.getOrderId();
        String text = "Hi " + customer.getFirstName() + " " + customer.getLastName() + ", /n" +
                "Your order has been placed for below items. If you not placed these order please do cancel. Or else " +
                "proceed with payment./n/n" ;
        mailServiceImpl.sendSimpleEmail(to, subject, text);
    }
}
