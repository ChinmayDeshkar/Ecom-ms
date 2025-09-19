package com.notification.NotificationService.service;

import com.notification.NotificationService.dto.Items;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service", url = "inventory-service:8085/inventory")
public interface InventoryClient {


    @GetMapping("/namebyid")
    public String getNameById(@RequestParam String id);

    @GetMapping("/unitPriceById")
    public Long getUnitPriceById(@RequestParam String id);
}