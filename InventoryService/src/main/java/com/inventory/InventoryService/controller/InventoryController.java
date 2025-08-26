package com.inventory.InventoryService.controller;

import com.inventory.InventoryService.model.Category;
import com.inventory.InventoryService.model.Inventory;
import com.inventory.InventoryService.service.InventoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("all")
    public List<Inventory> getAll(){
        return inventoryService.findAll();
    }

    @GetMapping("id={id}")
    public Inventory getById(@PathVariable Long id){
        return inventoryService.findById(id);
    }

    @GetMapping("/category={category}")
    public List<Inventory> getByCategory(@PathVariable Long category){
        return inventoryService.findByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addNew(@RequestBody Inventory inventory){
        try {
            Inventory addedInventory = inventoryService.addNewProduct(inventory);
            if (addedInventory != null) {
                return new ResponseEntity<>(Map.of("Message", "Product added "+ addedInventory.getId()), HttpStatus.CREATED);
            }
            else
                throw new RuntimeException("Error while adding product");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/update")
    public Inventory updateById(@RequestParam("id") Long id, @RequestBody Inventory inventory){
        return inventoryService.updateById(id, inventory);
    }
}
