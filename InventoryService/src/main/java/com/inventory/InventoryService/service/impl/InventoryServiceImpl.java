package com.inventory.InventoryService.service.impl;

import com.inventory.InventoryService.model.Category;
import com.inventory.InventoryService.model.Inventory;
import com.inventory.InventoryService.repo.CategoryRepo;
import com.inventory.InventoryService.repo.InventoryRepo;
import com.inventory.InventoryService.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

    // location to store uploaded images (configure in application.properties)
    @Value("${inventory.file.upload-dir:${file.upload-dir}}")
    private String uploadDir;

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
    public Inventory addNewProduct(Inventory inventory, MultipartFile file) throws IOException {
        Category category = categoryRepo.findById(inventory.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category does not exists!!"));
        String customId = "PRD" + (this.findAll().size() + 1);
        inventory.setId(customId);
        log.info(inventory.getId());

        if (file != null && !file.isEmpty()) {
//            Path uploadPath = Paths.get(System.getProperty("user.dir"), uploadDir);
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath();
            if (!Files.exists(uploadPath)) {
                log.info("{} not exist. creating new ", uploadDir);
                Files.createDirectories(uploadPath); // create folders if they don't exist


            }

            log.info("Coming here");
            log.info("{}: uploadPath",uploadPath);
            // Create unique filename
            String fileName = inventory.getId() + "_" + file.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            log.info("Transferring to {}", filePath);
            log.info("{}: User directory", System.getProperty("user.dir"));
            // Save file
            file.transferTo(filePath.toFile());

            // Save URL/path in DB
            inventory.setImageUrl("/uploads/" + fileName);
        }


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

    @Override
    public String getNameById(String id) {
        Inventory inventory = inventoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found!!"));
        return inventory.getName();
    }

    @Override
    public Long getUnitPriceById(String id) {
        Inventory inventory = inventoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Product not found!!"));
        return inventory.getUnitPrice();
    }

    public List<Category> getAllCategory(){
        return categoryRepo.findAll();
    }
}
