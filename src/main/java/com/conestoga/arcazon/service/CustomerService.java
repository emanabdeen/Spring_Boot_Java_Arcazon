package com.conestoga.arcazon.service;

import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;

    @Autowired
    public CustomerService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer saveCustomer(Customer customer) {
        // TO DO add validations
        return customerRepo.save(customer);
    }

    public Customer findCustomerById(long id) {
        return customerRepo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public Customer findByEmail(String email) {
        return customerRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public boolean emailExists(String email) {
        try {
            findByEmail(email);
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public List<Customer> findAllByNameOrEmail(String customerFirstName, String customerLastName, String customerEmail) {
        return customerRepo.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(customerFirstName, customerLastName, customerEmail);
    }

}
