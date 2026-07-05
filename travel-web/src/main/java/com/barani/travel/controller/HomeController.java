package com.barani.travel.controller;

import com.barani.travel.client.ProviderClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    ProviderClient providerClient;
    @GetMapping("/")
    public String home() {

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {

        return "register";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {

        if (session.getAttribute("TOKEN") == null) {
            return "redirect:/login";
        }

        return "dashboard";
    }

}