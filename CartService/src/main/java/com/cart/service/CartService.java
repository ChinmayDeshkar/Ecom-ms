package com.cart.service;

import com.cart.model.Cart;
import com.cart.model.CartId;

import java.util.List;

public interface CartService {

    Cart addToCart(Cart cart);
    void removeFromCart(CartId cartId);
    List<Cart> getByCustomerId(String CustomerId);
    void deleteByCustomer(String customerId);
}
