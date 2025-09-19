package com.customer.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerResponse {
    private String customerId;
    private String message;

    public CustomerResponse(String customerId, String message) {
        this.customerId = customerId;
        this.message = message;
    }
}
