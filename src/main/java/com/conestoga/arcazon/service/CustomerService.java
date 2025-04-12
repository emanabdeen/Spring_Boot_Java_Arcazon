package com.conestoga.arcazon.service;

import com.conestoga.arcazon.Utils.CustomerUtils;
import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.model.CustomerDto;
import com.conestoga.arcazon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomerService {
    private final CustomerRepository customerRepo;

    @Autowired
    public CustomerService(CustomerRepository customerRepo) {
        this.customerRepo = customerRepo;
    }

    public Customer findCustomerById(long id) {
        return customerRepo.findById(id).orElseThrow(() -> new NoSuchElementException("Customer not found"));
    }

    public List<Customer> findAll() {
        return customerRepo.findAll();
    }

    public List<Customer> findAllByNameOrEmail(String customerFirstName, String customerLastName, String customerEmail) {
        return customerRepo.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(customerFirstName, customerLastName, customerEmail);
    }

    @Transactional
    public void addCustomer(CustomerDto customerDto){

        Customer customer = CustomerUtils.dtoToEntity(customerDto);
        customerRepo.save(customer);
    }

    @Transactional
    public void updateCustomer(CustomerDto customerDto){

        Customer customer = CustomerUtils.dtoToEntity(customerDto);
        customerRepo.save(customer);
    }

    public void deleteCustomer(Long id){
        customerRepo.deleteById(id);

    }

}
