package com.customer.controller;

import com.customer.model.Customer;
import com.customer.model.CustomerResponse;
import com.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/customer")
@CrossOrigin(origins = "http://localhost:4200")
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
        log.info("CustomerId: " + customerId);
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

    @GetMapping("/exists/{customerId}")
    public ResponseEntity<Boolean> checkCustomerExists(@PathVariable String customerId) {
        Customer customer = customerService.getById(customerId);
        if (customer != null) {
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

}
