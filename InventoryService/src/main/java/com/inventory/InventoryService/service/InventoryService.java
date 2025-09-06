package com.inventory.InventoryService.service;

import com.inventory.InventoryService.model.Category;
import com.inventory.InventoryService.model.Inventory;

import java.util.List;

public interface InventoryService {

    List<Inventory> findAll();
    List<Inventory> findByCategory(Long category);
    Inventory findById(String id);
    Inventory addNewProduct(Inventory inventory);
    Inventory updateById(String id, Inventory inventory);
    public List<Category> getAllCategory();
    void deleteAll();
    String getNameById(String id);
    Long getUnitPriceById(String id);
}
