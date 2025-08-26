package com.customer.controller;

import com.customer.model.Customer;
import com.customer.model.CustomerResponse;
import com.customer.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/all")
    public List<Customer> getAll(){
        return customerService.getAll();
    }

    @PostMapping("/add")
    public ResponseEntity<CustomerResponse> addNewCustomer(@RequestBody Customer customer) {
        try {
            Customer savedCustomer = customerService.saveCustomer(customer);

            if (savedCustomer == null || savedCustomer.getCustomerId() == null) {
                throw new RuntimeException("Customer could not be saved");
            }

            CustomerResponse response = new CustomerResponse(savedCustomer.getCustomerId(), "Customer created successfully");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create customer", e);
        }
    }

    @GetMapping("/find/id={customerId}")
    public Customer findById(@PathVariable String customerId){
        return customerService.getById(customerId);
    }

    @DeleteMapping("/delete/id={customerId}")
    public void deleteById(@PathVariable String customerId){
        customerService.deleteById(customerId);
    }

    @PutMapping("/updateRole")
    public ResponseEntity<CustomerResponse> updateRole(@RequestParam String customerId, @RequestParam String newRole){
        try{
            Customer customer = customerService.updateRoleById(customerId, newRole);

            if (customer.getRole().equals(newRole)){
                CustomerResponse response = new CustomerResponse(customer.getCustomerId(), "Role Updated to: " + newRole);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            }
            else{
                throw new RuntimeException("Role not updated");
            }
        } catch (Exception e) {
            throw new RuntimeException("Something went wrong while updating role: " +e);
        }
    }
}
