package com.cart.controller;

import com.cart.model.Cart;
import com.cart.model.CartId;
import com.cart.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/cart")
@CrossOrigin(origins = "http://localhost:4200")
public class CartController {
    CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/addtocart")
    public Cart addToCart(@RequestBody Cart cart){
        return cartService.addToCart(cart);
    }

    @PostMapping("/removefromcart")
    public void delete(@RequestBody CartId cartId){
        log.info("---------------------------------------------------In delete---------------------------------------------------");
        log.info("CartId: " + cartId);
        cartService.removeFromCart(cartId);
    }

    @GetMapping("/{customerId}")
    public List<Cart> getByCustomerId(@PathVariable String customerId){
        return cartService.getByCustomerId(customerId);
    }

    @DeleteMapping("/delete/customerId={customerId}")
    public void deleteByCustomerId(@PathVariable String customerId){
        log.info("CustomerID: " + customerId);
        cartService.deleteByCustomer(customerId);
    }
}
