package com.inventory.InventoryService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.InventoryService.model.Category;
import com.inventory.InventoryService.model.Inventory;
import com.inventory.InventoryService.repo.CategoryRepo;
import com.inventory.InventoryService.repo.InventoryRepo;
import com.inventory.InventoryService.service.InventoryService;
import jakarta.ws.rs.DELETE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/inventory")
@CrossOrigin(origins = "http://localhost:4200")
public class InventoryController {

    private final InventoryService inventoryService;
    @Autowired
    private InventoryRepo inventoryRepo;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping("/all")
    public List<Inventory> getAll(){
        return inventoryService.findAll();
    }

    @GetMapping("/id={id}")
    public Inventory getById(@PathVariable String id){
        return inventoryService.findById(id);
    }

    @GetMapping("/namebyid")
    public String getNameById(@RequestParam String id){
        return inventoryService.getNameById(id);
    }

    @GetMapping("/unitPriceById")
    public Long getUnitPriceById(@RequestParam String id){
        return inventoryService.getUnitPriceById(id);
    }
    @GetMapping("/category={category}")
    public List<Inventory> getByCategory(@PathVariable Long category){
        return inventoryService.findByCategory(category);
    }

//    @PostMapping(
//            value = "/add",
//            consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }
//    )
//    public ResponseEntity<?> addNew(@RequestPart("product") Inventory inventory,
//                                    @RequestPart(value = "file", required = false) MultipartFile file){
//        try {
//            Inventory addedInventory = inventoryService.addNewProduct(inventory,file);
//            if (addedInventory != null) {
//                return new ResponseEntity<>(Map.of("Message", "Product added "+ addedInventory.getId()), HttpStatus.CREATED);
//            }
//            else
//                throw new RuntimeException("Error while adding product");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }

    @PostMapping("/add")
    public Inventory addNew(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam Long stock,
            @RequestParam Long unitPrice,
            @RequestParam Long categoryId,
            @RequestParam(required = false) MultipartFile image) throws Exception {

        Inventory inventory = new Inventory();
        inventory.setName(name);
        inventory.setDescription(description);
        inventory.setStock(stock);
        inventory.setUnitPrice(unitPrice);
        inventory.setCategoryId(categoryId);
        inventory.setId("PRD121");
        if (image != null && !image.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            Path filePath = Paths.get("uploads/" + fileName);
            Files.createDirectories(filePath.getParent());
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            inventory.setImageUrl("/uploads/" + fileName); // save path in DB
        }

        return inventoryRepo.save(inventory);

    }

//    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<Inventory> addProduct(
//            @RequestParam("name") String name,
//            @RequestParam("description") String description,
//            @RequestParam("unitPrice") Long unitPrice,
//            @RequestParam("stock") Long stock,
//            @RequestParam("categoryId") Long categoryId,
//            @RequestParam("file") MultipartFile file
//    ) {
//        // Save file somewhere (DB, FileSystem, S3, etc.)
//        String fileName = file.getOriginalFilename();
//        System.out.println("Uploaded file: " + fileName);
//
//        Inventory product = new Inventory();
//        product.setName(name);
//        product.setDescription(description);
//        product.setUnitPrice(unitPrice);
//        product.setStock(stock);
//        product.setCategoryId(categoryId);
//        product.setImageUrl(fileName); // save file path/URL in DB
//        log.info("Product : {}", product);
//        return ResponseEntity.ok(inventoryService.addNewProduct(product));
//    }

    @PutMapping("/update")
    public Inventory updateById(@RequestParam("id") String id, @RequestBody Inventory inventory){
        return inventoryService.updateById(id, inventory);
    }

    @GetMapping("/category/all")
    public List<Category> getAllCategory(){
        return inventoryService.getAllCategory();
    }

    @DeleteMapping("/delete")
    public void delete(){
        inventoryService.deleteAll();
    }
}
