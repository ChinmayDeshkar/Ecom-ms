package com.notification.NotificationService.config;

import com.notification.NotificationService.dto.Customer;
import com.notification.NotificationService.dto.Items;
import com.notification.NotificationService.dto.Order;
import com.notification.NotificationService.dto.Topics;
import com.notification.NotificationService.service.CustomerClient;
import com.notification.NotificationService.service.InventoryClient;
import com.notification.NotificationService.service.impl.MailServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@Slf4j
@Configuration
public class KafkaConsumerConfig {

    @Autowired
    private MailServiceImpl mailServiceImpl;

    @Autowired
    private CustomerClient customerClient;

    @Autowired
    private InventoryClient inventoryClient;

    @KafkaListener(topics = "order-placed", groupId = "group-1")
    public void newOrder(@Payload Order order) {
        log.info("--------------------------------------------------------------------------------------------");
        log.info("Received order: {}", order);

        try {
            Customer customer = customerClient.getCustomerDetails(order.getCustomerId());
            if (customer == null) {
                log.error("Customer not found for id {}", order.getCustomerId());
                return;
            }

            String to = customer.getEmailId();
            String subject = "Congratulations! Order has been placed!! Order No. " + order.getOrderId();

            StringBuilder text = new StringBuilder();
            text.append("<h3>Hi ").append(customer.getFirstName()).append(" ").append(customer.getLastName()).append(",</h3>");
            text.append("<p>Your order has been successfully placed. Below are the order details:</p>");

            text.append("<table style='width:100%; border-collapse: collapse;'>");
            text.append("<thead>");
            text.append("<tr>");
            text.append("<th style='border:1px solid #ddd; padding: 8px;'>Item Name</th>");
            text.append("<th style='border:1px solid #ddd; padding: 8px;'>Quantity</th>");
            text.append("<th style='border:1px solid #ddd; padding: 8px;'>Unit Price</th>");
            text.append("<th style='border:1px solid #ddd; padding: 8px;'>Total</th>");
            text.append("</tr>");
            text.append("</thead>");
            text.append("<tbody>");

            double totalAmount = 0.0;

            for (Items item : order.getItems()) {
                String itemName = inventoryClient.getNameById(item.getItemId());
                Long unitPrice = inventoryClient.getUnitPriceById(item.getItemId());
                if (unitPrice == null) unitPrice = 0L;

                double itemTotal = item.getQuantity() * unitPrice;
                totalAmount += itemTotal;

                text.append("<tr>");
                text.append("<td style='border:1px solid #ddd; padding: 8px;'>").append(itemName).append("</td>");
                text.append("<td style='border:1px solid #ddd; padding: 8px;'>").append(item.getQuantity()).append("</td>");
                text.append("<td style='border:1px solid #ddd; padding: 8px;'>").append(unitPrice).append("</td>");
                text.append("<td style='border:1px solid #ddd; padding: 8px;'>").append(itemTotal).append("</td>");
                text.append("</tr>");
            }

            text.append("</tbody>");
            text.append("</table>");
            text.append("<p><strong>Total Amount: </strong>").append(order.getTotalPayableAmount()).append("</p>");

            text.append("<p>If you did not place this order, please contact support immediately.</p>");
            text.append("<p>Thank you for shopping with us!</p>");

            log.info("Email content:\n{}", text.toString());

            mailServiceImpl.sendSimpleEmail(to, subject, text.toString());

        } catch (Exception e) {
            log.error("Error processing order: {}", order.getOrderId(), e);
        }

        log.info("--------------------------------------------------------------------------------------------");
    }
}
