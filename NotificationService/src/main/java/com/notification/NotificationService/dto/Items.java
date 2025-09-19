package com.notification.NotificationService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Items {
    String itemId;
    String itemName;
    String description;
    Long unitPrice;
    Long quantity;


}