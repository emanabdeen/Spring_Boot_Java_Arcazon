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

    public List<CustomerDto> findAll() {
        return CustomerUtils.listEntityToListDto(customerRepo.findAll());
    }

    public List<CustomerDto> findAllByNameOrEmail(String customerFirstName, String customerLastName, String customerEmail) {
        return CustomerUtils.listEntityToListDto(
                customerRepo.findAllByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCaseOrEmailContainingIgnoreCase(customerFirstName, customerLastName, customerEmail
                ));
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

}
