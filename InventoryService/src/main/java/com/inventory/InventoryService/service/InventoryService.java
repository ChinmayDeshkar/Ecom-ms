package com.inventory.InventoryService.service;

import com.inventory.InventoryService.model.Category;
import com.inventory.InventoryService.model.Inventory;

import java.util.List;

public interface InventoryService {

    List<Inventory> findAll();
    List<Inventory> findByCategory(Long category);
    Inventory findById(Long id);
    Inventory addNewProduct(Inventory inventory);
    Inventory updateById(Long id, Inventory inventory);
}
