package com.order.service;

import com.order.model.Inventory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8085", value = "Inventory-client")
public interface InventoryClient {

    @GetMapping("/inventory/id={id}")
    public Inventory getById(@PathVariable String id);
}
