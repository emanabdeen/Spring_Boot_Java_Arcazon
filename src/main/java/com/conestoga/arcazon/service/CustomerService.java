package com.conestoga.arcazon.service;

import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;

    @Autowired
    public CustomerService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer findCustomerById(long id) {
        return customerRepo.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public List<Customer> findAllByNameOrEmail(String customerFirstName, String customerLastName, String customerEmail) {
        return customerRepo.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(customerFirstName, customerLastName, customerEmail);
    }

}
