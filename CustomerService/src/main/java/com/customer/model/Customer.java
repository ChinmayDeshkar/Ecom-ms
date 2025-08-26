package com.customer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    @Id
    String customerId;
    String firstName;
    String lastName;
    String emailId;
    String role;
    @Column(nullable = false, updatable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    LocalDateTime dte_Created;

    // Lifecycle hooks
    @PrePersist
    protected void onCreate() {
        this.dte_Created = LocalDateTime.now();
    }

}
