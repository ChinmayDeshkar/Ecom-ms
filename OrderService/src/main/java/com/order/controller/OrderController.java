package com.order.controller;

import com.order.model.Order;
import com.order.model.OrderResponse;
import com.order.model.Items;
import com.order.service.OrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/all")
    public List<Order> getAll(){
        return orderService.getAllOrders();
    }

    @PostMapping("/add")
    public ResponseEntity<OrderResponse> addOrder(@RequestBody Order order){
        try{
            Order createdOrder = orderService.addOrder(order);
            if(order == null || order.getOrderId() == null)
                throw new RuntimeException("Order not being added!!");

            else{
                OrderResponse response = new OrderResponse(createdOrder.getOrderId(), "Order has been created");
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/order/{id}")
    public Order findById(@PathVariable String id){
        return orderService.getByOrderId(id);
    }

    @PutMapping("/status-update/orderid={OrderId}&status={newStatus}")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable String OrderId, @PathVariable String newStatus){
        try{
            Order order = orderService.updateStatusById(OrderId, newStatus);
            OrderResponse reponse = new OrderResponse(order.getOrderId(), "Order Status has been updated!!");
            return ResponseEntity.status(HttpStatus.OK).body(reponse);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/orders/customerid={id}")
    public List<Order> getOrderByCustomerId(@PathVariable String id){
        return orderService.getByCustomerId(id);
    }

    @PutMapping("/addItem/orderId={id}")
    public ResponseEntity<OrderResponse> addItemInList(@PathVariable String id, @RequestBody Items item){
        try{
            Order order = orderService.addItemsInOrder(id, item);
            OrderResponse response = new OrderResponse(order.getOrderId(), "Item added to order!!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/delete-item/orderId={orderId}/itemId={itemId}")
    public ResponseEntity<OrderResponse> removeItemFromOrder(@PathVariable String orderId, @PathVariable String itemId){
        try {
            Order order = orderService.removeItemFromOrder(orderId, itemId);
            OrderResponse response = new OrderResponse(order.getOrderId(), "Item is removed from order!!");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {throw new RuntimeException(e);

        }
    }

    @DeleteMapping("/delete/orderId={id}")
    public void deleteOrder(@PathVariable String id){
        orderService.deleteOrder(id);
    }

    @GetMapping("/between")
    public List<Order> getOrdersBetween(@RequestParam("start")
                                                 @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime startDate,

                                          @RequestParam("end")
                                                 @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss") LocalDateTime endDate){
        return orderService.findOrdersBetween(startDate, endDate);
    }

    @GetMapping("/status")
    public List<Order> findOrdersByStatus(@RequestParam("status") String status){
        return orderService.findOrdersByStatus(status);
    }
}
