package com.conestoga.arcazon.service;

import com.conestoga.arcazon.utils.CustomerUtils;
import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.model.CustomerDto;
import com.conestoga.arcazon.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InvalidObjectException;
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

    public List<CustomerDto> findAllByNameOrEmail(String name,String email) {

        /*
            turns empty into null so it's not returned in the sql
            SpringJPA does like comparison, and "like empty" bring everything.
         */
        if(name != null){
            name = name.isEmpty()? null : name; //checks if name is empty, then turns into null
        }

        if(email != null){
            email = email.isEmpty()? null : email; //checks if name is empty, then turns into null
        }

        return CustomerUtils.listEntityToListDto(
                customerRepo.searchByNameAndEmail(name, email
                ));
    }

    @Transactional
    public void addCustomer(CustomerDto customerDto) throws Exception {

        try {

            boolean emailExists = emailExists(customerDto);
            if (!emailExists) {
                Customer customer = CustomerUtils.dtoToEntity(customerDto);
                customerRepo.save(customer);
            } else {
                throw new InvalidObjectException("Email already exists");
            }
        } catch (InvalidObjectException e) { //throws specific exception to handle on the view layer -useful for REST
            throw new InvalidObjectException(e.getMessage());
        } catch (Exception e) {
            throw new Exception(e.getMessage()); //throws exception for upper layers to handle
        }


    }

    @Transactional
    public void updateCustomer(CustomerDto customerDto) throws Exception {
        try {

            boolean emailExists = emailExists(customerDto);

            if (emailExists) {
                throw new InvalidObjectException("Email already exists");
            } else {
                Customer customer = CustomerUtils.dtoToEntity(customerDto);
                customerRepo.save(customer);
            }

        }catch (InvalidObjectException e){
            e.printStackTrace();
            throw new InvalidObjectException(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }

    public void deleteCustomer(Long id) {
        try {

            //#TODO: Call order service to check if customer has orders before deleting

            customerRepo.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Customer findByEmail(String email, Long id) {
        if (id != null) {
            return customerRepo.findByEmailAndIdNot(email, id)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
        } else {

            return customerRepo.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Customer not found"));
        }
    }

    public boolean emailExists(CustomerDto dto) {
        try {
            findByEmail(dto.getEmail(), dto.getId());
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

}
