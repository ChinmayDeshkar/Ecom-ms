package com.notification.NotificationService.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    String orderId;
    String customerId;
    List<Items> items;
    Long taxAmount;
    Long discount;
    Long totalPayableAmount;
    String status;
    LocalDateTime dteCreated;
    LocalDateTime dteUpdated;



}
