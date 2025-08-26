package com.notification.NotificationService.service;

import com.notification.NotificationService.dto.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8081",value = "Customer-Client")
//@FeignClient(name = "QUESTION-SERVICE")
public interface CustomerClient {

    @GetMapping("/customer/find/id={customerId}")
    public Customer getCustomerDetails(@PathVariable String customerId);
}