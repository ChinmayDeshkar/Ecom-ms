package com.inventory.InventoryService.model;

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
    Long taxRate;
    Long discount;
    Long total;
    String status;
    LocalDateTime dteCreated;
    LocalDateTime dteUpdated;

}
