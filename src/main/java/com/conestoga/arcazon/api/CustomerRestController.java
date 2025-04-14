package com.conestoga.arcazon.api;

import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.model.CustomerDto;
import com.conestoga.arcazon.service.CustomerService;
import com.conestoga.arcazon.utils.CustomerUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InvalidObjectException;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {


    private CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Object> getAllCustomers(@RequestParam(required = false) String name, @RequestParam(required = false) String email) {

        try {

            List<CustomerDto> customers = new ArrayList<>();

            //sanitize optional filters if both null or empty, ignore
            name = (name != null && name.isEmpty()) ? null : name;
            email = (email != null && email.isEmpty()) ? null : email;

            if ((name != null) || (email != null)) {

                customers = customerService.findAllByNameOrEmail(name, email);

            }else{
                customers = customerService.findAll();
            }

            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(
                    Collections.singletonMap("error","Failed to retrieve customers"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getCustomerById(@PathVariable Long id) {

        try {

            if (id.toString().isEmpty() || id == null) {
                return ResponseEntity.badRequest().build();
            }

            Customer customer = new Customer();
            customer = customerService.findCustomerById(id);

            return ResponseEntity.ok(customer);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(
                    Collections.singletonMap("error","Failed to retrieve customer"));
        }
    }




    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addCustomer(@RequestBody CustomerDto customerDto) {

        try {

            if(CustomerUtils.validateCustomer(customerDto)){
                CustomerDto newCustomer = customerService.addCustomer(customerDto);

                URI location = URI.create("/customers/"+newCustomer.getId());
                return ResponseEntity.created(location).body(newCustomer);
            }else{

                return ResponseEntity.badRequest().body(
                        Collections.singletonMap("error","Invalid customer object"));
            }

        } catch (InvalidObjectException e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(
                    Collections.singletonMap("error",e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(
                    Collections.singletonMap("error","Failed to add customer"));
        }
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> updateCustomer(@RequestBody CustomerDto customerDto) {
        try {
            if(CustomerUtils.validateCustomer(customerDto)){


                customerService.updateCustomer(customerDto);

                CustomerDto savedCustomer = CustomerUtils.entityToDto(customerService.findCustomerById(customerDto.getId()));

                return ResponseEntity.ok().body(savedCustomer);
            }else{

                return ResponseEntity.badRequest().body(
                        Collections.singletonMap("error","Invalid customer object"));
            }

        } catch (InvalidObjectException e) {
            e.printStackTrace();
            return ResponseEntity.unprocessableEntity().body(
                    Collections.singletonMap("error",e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(
                    Collections.singletonMap("error","Failed to update customer"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        try {

            if (id.toString().isEmpty() || id == null) {
                return ResponseEntity.badRequest().build();
            }

            customerService.deleteCustomer(id);
            return ResponseEntity.noContent().build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(
                    Collections.singletonMap("error","Failed to delete customer"));
        }
    }


}
