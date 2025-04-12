package com.conestoga.arcazon.controller;

import com.conestoga.arcazon.model.Customer;
import com.conestoga.arcazon.service.CustomerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private  final CustomerService customerService;
    public CustomerController(CustomerService customerService) {
        this.customerService=customerService;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "customers/login";
    }

    @PostMapping("/login")
    public String processLogin(String email,HttpSession session,Model model) {
        try {
            Customer customer = customerService.findByEmail(email);

            session.setAttribute("customer", customer);
            return "redirect:/products"; // This should match your ProductController mapping
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "dashboard"; //--------------------------<<< can be changed to order page
        }
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute("customer") Customer customer, Model model) {
        try {
            // Check if email already exists
            if (customerService.emailExists(customer.getEmail())) {
                model.addAttribute("error", "Email already exists. Please use a different email.");
                return "customers/signup";
            }
            // Set timestamps
            customer.setCreatedAt(Instant.now());
            customer.setUpdatedAt(Instant.now());

            // Save customer
            customerService.saveCustomer(customer);
            return "redirect:/customers/login";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed: " + e.getMessage());
            return "customers/signup";
        }
    }

}
