package com.inventory.InventoryService.repo;

import com.inventory.InventoryService.model.Category;
import com.inventory.InventoryService.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Long> {
    List<Inventory> findByCategoryId(Long category);

}
