package com.order.repo;

import com.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, String> {
    List<Order> findByCustomerId(String customerId);
    List<Order> findByDteCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Order> findByStatus(String status);

}
