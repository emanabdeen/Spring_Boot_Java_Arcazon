package com.conestoga.arcazon.api;

import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.model.CustomerDto;
import com.conestoga.arcazon.service.CustomerService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {


    private CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers(){

        try{

            List<Customer> customers = new ArrayList<>();
            customers = customerService.findAll();

            return ResponseEntity.ok(customers);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id){

        try{

            if(id.toString().isEmpty() || id == null){
                return ResponseEntity.badRequest().build();
            }

            Customer customer = new Customer();
            customer = customerService.findCustomerById(id);

            return ResponseEntity.ok(customer);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return ResponseEntity.noContent().build();

        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @GetMapping("/search")
    public ResponseEntity<List<Customer>> getByNameOrEmail(@RequestParam(required = false) String name, @RequestParam(required = false) String email){

        try{
            //checks if at least one of the params is provided
            if((name == null || name.isEmpty()) && ( email == null || email.isEmpty() ) ){
                return ResponseEntity.badRequest().build();
            }

            List<Customer> customerList = customerService.findAllByNameOrEmail(name,name, email);
            return ResponseEntity.ok(customerList);

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addCustomer(@RequestBody CustomerDto customerDto){

        try{
            customerService.addCustomer(customerDto);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDto customerDto){
        try{
            customerService.updateCustomer(customerDto);
            return ResponseEntity.noContent().build();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id){
        try{

            if(id.toString().isEmpty() || id == null){
                return ResponseEntity.badRequest().build();
            }

            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();

        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }








}
