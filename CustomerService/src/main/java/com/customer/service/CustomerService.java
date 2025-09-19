package com.customer.service;


import com.customer.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAll();
    Customer getById(String customerId);
    Customer saveCustomer(Customer customer);
    void deleteById(String customerId);
    Customer updateRoleById(String customerId, String newRole);
}
