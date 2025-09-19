package com.customer.service.impl;

import com.customer.model.Customer;
import com.customer.model.Role;
import com.customer.repo.CustomerRepo;
import com.customer.service.CustomerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepo customerRepo;

    public CustomerServiceImpl(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    @Override
    public List<Customer> getAll() {
        return customerRepo.findAll();
    }

    @Override
    public Customer getById(String customerId) {
        return customerRepo.findByCustomerId(customerId);
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        int numberofUsers = this.getAll().size();
        String customId = "ext" +  (numberofUsers + 1);
        customer.setCustomerId(customId);
        customer.setRole(Role.CUSTOMER_ROLE.toString());
        return customerRepo.save(customer);
    }

    @Override
    public void deleteById(String customerId) {
         customerRepo.deleteById(customerId);
    }

    @Override
    public Customer updateRoleById(String customerId, String newRole) {
        Customer customer = getById(customerId);
        customer.setRole(newRole);
        customerRepo.save(customer);
        return  customer;
    }
}
