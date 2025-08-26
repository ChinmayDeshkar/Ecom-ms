package com.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tb_order")
public class Order {
    @Id
    String orderId;
    String customerId;
    @ElementCollection
    List<Items> items;
    @Transient
    Long taxRate;
    @Transient
    Long discount;
    Long total;
    String status;
    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime dteCreated;
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime dteUpdated;

    // Lifecycle hooks
    @PrePersist
    protected void onCreate() {
        this.dteCreated = LocalDateTime.now();
        this.dteUpdated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.dteUpdated = LocalDateTime.now();
    }
}
