package com.inventory.InventoryService.config;

import com.inventory.InventoryService.model.AppConstants;
import com.inventory.InventoryService.model.Inventory;
import com.inventory.InventoryService.model.Order;
import com.inventory.InventoryService.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;

@Configuration
public class KafkaConsumerConfig {
    @Autowired
    private InventoryService inventoryService;

    @KafkaListener(topics = AppConstants.orderPlacedTopic, groupId = "group-1")
    public void newOrder(@Payload Order order){
        System.out.println(order);
        for (int i = 0; i < order.getItems().size(); i++) {
            String productid = order.getItems().get(i).getItemId();
            Inventory inventory = inventoryService.findById(productid);
            inventory.setStock(inventory.getStock() - order.getItems().get(i).getQuantity());
            inventoryService.updateById(productid, inventory);
        }
    }
}
