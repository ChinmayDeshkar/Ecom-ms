package com.inventory.InventoryService.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Inventory {
    @Id
    String id;
    String name;
    String description;
    Long stock;
    Long unitPrice;
    Long categoryId;

    String imageUrl; // Store Image URL here

}
