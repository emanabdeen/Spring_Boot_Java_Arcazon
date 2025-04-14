package com.conestoga.arcazon.controller;

import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.model.CustomerDto;
import com.conestoga.arcazon.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;


    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }


    @GetMapping("/login")
    public String showLoginForm() {
        return "customers/login";
    }

    @PostMapping("/login")
    public String processLogin(String email, HttpSession session, Model model) {
        try {
            Customer customer = customerService.findByEmail(email,null);

            session.setAttribute("customer", customer);
            return "redirect:/"; // This should match your ProductController mapping
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/";
        }
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("customer") CustomerDto customerDto, Model model) {
        try {
            // Check if email already exists
            if (customerService.emailExists(customerDto)) {
                model.addAttribute("error", "Email already exists. Please use a different email.");
                return "customers/signup";
            }

            // Save customer
            customerService.addCustomer(customerDto);
            return "redirect:/customers/login";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "customers/signup";
        }
    }


    @GetMapping
    public String getAllCustomers(Model model, @RequestParam(required = false) String name, @RequestParam(required = false) String email){

        List<CustomerDto> customerDtoList = new ArrayList<>();
        //if there's at least one filter
        if((name != null && !name.trim().isEmpty()) || (email != null &&  !email.trim().isEmpty())){

            customerDtoList = customerService.findAllByNameOrEmail(name,email);
        }else {

            customerDtoList = this.customerService.findAll();
        }
        model.addAttribute("customers", customerDtoList);
        model.addAttribute("name", name);
        model.addAttribute("email", email);
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
    public String addCustomer(@ModelAttribute CustomerDto dto, RedirectAttributes redirectAttributes){

       try {
           customerService.addCustomer(dto);
           redirectAttributes.addFlashAttribute("success", "Customer added successfully!");
           return "redirect:/customers";
       }catch (InvalidObjectException e){
           redirectAttributes.addFlashAttribute("error", e.getMessage() );
           return "redirect:/customers/add";
       }catch (Exception e){
           redirectAttributes.addFlashAttribute("error", "Failed to add customer " );
           return "redirect:/customers/add";
       }

    }

    @GetMapping("/{id}/edit")
    public String getCustomerToEditById(@PathVariable Long id, Model model){

        Customer customer = new Customer();
        customer = customerService.findCustomerById(id);

        model.addAttribute("customer",customer);

        return "customers/customer-edit.html";

    }

    @PostMapping("/{id}")
    public String postCustomerToEditById(@PathVariable Long id, @ModelAttribute CustomerDto dto, RedirectAttributes redirectAttributes) {

        try {
            if (id != null) {
                customerService.updateCustomer(dto);
                redirectAttributes.addFlashAttribute("success", "Customer updated successfully!");
                return "redirect:/customers";
            }else{
                throw new Exception("Failed to update customer");
            }
       }catch (InvalidObjectException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/customers/" + id + "/edit";
        }catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Failed to update customer" );
            return "redirect:/customers/"+id+"/edit";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id){
        customerService.deleteCustomer(id);
        return "redirect:/customers";

    }

    @GetMapping("/customer-add")
    public String addCustomer(){
        return "customers/customer-add.html";
    }

    @GetMapping("/dashboard")
    public String redirectToDashboard(){
        return "dashboard.html";
    }
}
