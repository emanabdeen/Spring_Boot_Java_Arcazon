package com.conestoga.arcazon.controller;

import com.conestoga.arcazon.Utils.CustomerUtils;
import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.model.CustomerDto;
import com.conestoga.arcazon.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }



    @GetMapping
    public String getAllCustomers(Model model, @RequestParam(required = false) String name, @RequestParam(required = false) String email){

        List<CustomerDto> customerDtoList = new ArrayList<>();
        //if there's at least one filter
        if((name != null && !name.isEmpty()) || (email != null &&  !email.isEmpty())){
            customerDtoList = customerService.findAllByNameOrEmail(name,name,email);
        }else {

            customerDtoList = this.customerService.findAll();
        }
        model.addAttribute("customers", customerDtoList);
        return "customers/customers.html";
    }


    @GetMapping("/{id}")
    public String getCustomerById(@PathVariable Long id, Model model){

            Customer customer = new Customer();
            customer = customerService.findCustomerById(id);

            model.addAttribute("customer",customer);

            return "customers/customer-details.html";

    }


    @PostMapping
    public String addCustomer(@ModelAttribute CustomerDto dto){
        customerService.addCustomer(dto);
        return "redirect:/customers";
    }

    @GetMapping("/{id}/edit")
    public String getCustomerToEditById(@PathVariable Long id, Model model){

        Customer customer = new Customer();
        customer = customerService.findCustomerById(id);

        model.addAttribute("customer",customer);

        return "customers/customer-edit.html";

    }

    @PostMapping("/{id}")
    public String postCustomerToEditById(@PathVariable Long id, @ModelAttribute CustomerDto dto) {
        if (id != null) {

            customerService.updateCustomer(dto);
        }

        return  "redirect:/customers";

    }

    @GetMapping
    public String deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return "redirect:/customers";

    }

    @GetMapping("/add")
    public String addCustomer(){
        return "customers/customer-add.html";
    }

}
