package com.cart.model;

import java.io.Serializable;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class CartId implements Serializable {
    private String customerId;
    private String productId;
}
