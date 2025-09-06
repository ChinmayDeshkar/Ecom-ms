package com.cart.service;

import com.cart.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8085" ,value = "Inventory-client")
public interface InventoryClient {

    @GetMapping("/inventory/id={id}")
    Inventory getItemById(@PathVariable String id);
}
