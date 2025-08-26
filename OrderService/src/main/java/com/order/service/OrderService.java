package com.order.service;

import com.order.model.Order;
import com.order.model.Items;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();
    Order getByOrderId(String OrderId);
    List<Order> getByCustomerId(String customerId);
    Order updateStatusById(String OrderId, String newStatus);
    Order addOrder(Order order);
    Order addItemsInOrder(String OrderId, Items item);
    Order removeItemFromOrder(String OrderId, String itemId);
    void deleteOrder(String OrderId);
    List<Order> findOrdersBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Order> findOrdersByStatus(String status);
}
