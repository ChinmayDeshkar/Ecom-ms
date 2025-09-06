package com.cart.repo;

import com.cart.model.Cart;
import com.cart.model.CartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepo extends JpaRepository<Cart, CartId> {
    List<Cart> findByCustomerId(String customerId);
    void deleteByCustomerId(String customerId);

}
