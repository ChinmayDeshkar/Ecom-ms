package com.inventory.InventoryService.service.impl;

import com.inventory.InventoryService.model.Category;
import com.inventory.InventoryService.model.Inventory;
import com.inventory.InventoryService.repo.CategoryRepo;
import com.inventory.InventoryService.repo.InventoryRepo;
import com.inventory.InventoryService.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepo inventoryRepo;
    private final CategoryRepo categoryRepo;

    public InventoryServiceImpl(CategoryRepo categoryRepo, InventoryRepo inventoryRepo) {
        this.categoryRepo = categoryRepo;
        this.inventoryRepo = inventoryRepo;
    }

    @Override
    public List<Inventory> findAll() {
        return inventoryRepo.findAll();
    }

    @Override
    public List<Inventory> findByCategory(Long category) {
        return inventoryRepo.findByCategoryId(category);
    }

    @Override
    public Inventory findById(String id) {
        return inventoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Product Not Found"));
    }

    @Override
    public Inventory addNewProduct(Inventory inventory) {
        Category category = categoryRepo.findById(inventory.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category does not exists!!"));
        String customId = "PRD" + (this.findAll().size() + 1);
        inventory.setId(customId);
        log.info(inventory.getId());
        return inventoryRepo.save(inventory);
    }

    @Override
    public Inventory updateById(String id, Inventory inventory) {
        Inventory inventory1 = findById(id);
        if (inventory1 == null) {
            log.error("Product does not exists with id: {}", id);
            throw new RuntimeException("Product not found");
        }
        return inventoryRepo.save(inventory);
    }

    @Override
    public void deleteAll(){
        inventoryRepo.deleteAll();
    }
}
