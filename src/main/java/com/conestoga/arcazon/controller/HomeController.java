package com.conestoga.arcazon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String showDashboard() {
        return "dashboard";
    }
    @GetMapping("/orders-dashboard")
    public String showOrdersDashboard() {
        return "orders-dashboard";
    }

}
