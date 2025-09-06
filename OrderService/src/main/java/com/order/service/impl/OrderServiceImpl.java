package com.order.service.impl;

import com.order.model.Customer;
import com.order.model.Inventory;
import com.order.model.Order;
import com.order.model.Items;
import com.order.repo.OrderRepo;
import com.order.service.CustomerClient;
import com.order.service.InventoryClient;
import com.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final CustomerClient customerClient;
    private final KafkaServiceImpl kafkaServiceImpl;
    private final InventoryClient inventoryClient;

    public OrderServiceImpl(CustomerClient customerClient, OrderRepo orderRepo, KafkaServiceImpl kafkaServiceImpl, InventoryClient inventoryClient) {
        this.customerClient = customerClient;
        this.orderRepo = orderRepo;
        this.kafkaServiceImpl = kafkaServiceImpl;
        this.inventoryClient = inventoryClient;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @Override
    public Order getByOrderId(String OrderId) {
        return orderRepo.findById(OrderId).orElseThrow(() -> new RuntimeException("Order Not Found"));
    }

    @Override
    public List<Order> getByCustomerId(String customerId) {
        return orderRepo.findByCustomerId(customerId);
    }

    @Override
    public Order updateStatusById(String OrderId, String newStatus) {
        Order order = getByOrderId(OrderId);
        order.setStatus(newStatus);
        orderRepo.save(order);
        return order;
    }

    @Override
    public Order addOrder(Order order) {
        log.info("Order: ---" + order);
        Long total = 0L;
        Customer customer = customerClient.getCustomerDetails(order.getCustomerId());
        if(customer == null) throw new RuntimeException("Customer Id is invalid!!");
        for (Items item : order.getItems()) {
            Inventory inventory = inventoryClient.getById(item.getItemId());
            if(inventory.getStock() >= 0){
                throw new RuntimeException(inventory.getName() + " is out of stock!!");
            }
        }
        int size = getAllOrders().size();
        order.setOrderId("ORD" + (size + 1));
        boolean isMessageSent = kafkaServiceImpl.sendNewOrder(order);
        if (isMessageSent)
            return orderRepo.save(order);
        else
            throw new RuntimeException("Unable to send message to Invoice service");
    }

    @Override
    public Order addItemsInOrder(String OrderId, Items item) {
        Order order = getByOrderId(OrderId);
        order.getItems().add(item);

        return addOrder(order);
    }

    @Override
    public Order removeItemFromOrder(String OrderId, String itemId) {

        Order order = getByOrderId(OrderId);
        boolean removed = order.getItems().removeIf(item -> item.getItemId().equals(itemId));
        if(!removed){
            throw new RuntimeException("Item not found!!");
        }
        return orderRepo.save(order);
    }

    @Override
    public void deleteOrder(String OrderId) {
        Order order = getByOrderId(OrderId);
        orderRepo.deleteById(OrderId);
    }

    @Override
    public List<Order> findOrdersBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepo.findByDteCreatedBetween(startDate, endDate);
    }

    @Override
    public List<Order> findOrdersByStatus(String status) {
        return orderRepo.findByStatus(status);
    }
}
