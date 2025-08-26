package com.notification.NotificationService.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    String customerId;
    String firstName;
    String lastName;
    String emailId;
    String role;
}
