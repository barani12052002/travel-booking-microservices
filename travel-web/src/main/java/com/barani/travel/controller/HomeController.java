package com.barani.travel.controller;

import com.barani.travel.client.BookingHistoryClient;
import com.barani.travel.client.ProviderClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private BookingHistoryClient bookingHistoryClient;
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
    public String dashboard(HttpSession session, Model model) {

        String username = (String) session.getAttribute("USERNAME");
        String email = (String) session.getAttribute("EMAIL");
        String token = "Bearer " + session.getAttribute("TOKEN");

        model.addAttribute("username", username);

        int bookingCount = bookingHistoryClient
                .getBookings(token, email)
                .size();

        model.addAttribute("bookingCount", bookingCount);

        model.addAttribute("tourCount",
                providerClient.getAttractions().size());

        model.addAttribute("destinationCount",
                providerClient.getAttractions().size());

        model.addAttribute("profileComplete", "100%");

        return "dashboard";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }
}