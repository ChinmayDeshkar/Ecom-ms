package com.order.model;

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
