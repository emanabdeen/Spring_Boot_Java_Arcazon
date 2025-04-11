package com.conestoga.arcazon.service;

import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.model.Order;
import com.conestoga.arcazon.repository.CustomerRepository;
import com.conestoga.arcazon.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;
    private final OrderRepository orderRepo;

    @Autowired
    public CustomerService(CustomerRepository customerRepo, OrderRepository orderRepo) {
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
    }

    public Customer findCustomerById(long id) {
        return customerRepo.findById(id).orElseThrow(()-> new RuntimeException("Customer not found"));
    }

    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public List<Customer> findAllByNameOrEmail(String customerFirstName, String customerLastName,String customerEmail) {
        return customerRepo.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(customerFirstName,customerLastName,customerEmail);
    }

    public List<Order> findAllOrdersByCustomerId(long id) {
        return orderRepo.findAllByCustomer_Id(id);
    }
}
