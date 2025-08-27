package com.inventory.InventoryService.repo;

import com.inventory.InventoryService.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category, Long> {
}
