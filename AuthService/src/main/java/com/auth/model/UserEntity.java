package com.auth.model;

import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "tb_user")
public class UserEntity {
    String userId;
    String password;
    String role;
    LocalDate dte_created;

    @PrePersist

}
