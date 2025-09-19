package com.cart.service.impl;

import com.cart.model.Cart;
import com.cart.model.CartId;
import com.cart.repo.CartRepo;
import com.cart.service.CartService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    private final CartRepo cartRepo;

    public CartServiceImpl(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }


    @Override
    public Cart addToCart(Cart cart) {
        return cartRepo.save(cart);
    }

    @Override
    public void removeFromCart(CartId cartId) {
        log.info("Here in Remove from cart");
        cartRepo.deleteById(cartId);
    }

    @Override
    public List<Cart> getByCustomerId(String customerId) {
        return cartRepo.findByCustomerId(customerId);
    }

    @Override
    @Transactional
    public void deleteByCustomer(String customerId) {
        cartRepo.deleteByCustomerId(customerId);
    }

}
