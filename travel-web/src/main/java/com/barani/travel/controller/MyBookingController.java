package com.barani.travel.controller;

import com.barani.travel.client.BookingHistoryClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyBookingController {

    private final BookingHistoryClient bookingHistoryClient;

    public MyBookingController(BookingHistoryClient bookingHistoryClient) {
        this.bookingHistoryClient = bookingHistoryClient;
    }

    @GetMapping("/bookings")
    public String bookings(HttpSession session, Model model) {

        String email = (String) session.getAttribute("EMAIL");

        String token = "Bearer " + session.getAttribute("TOKEN");

        System.out.println("EMAIL = " + email);
        System.out.println("TOKEN = " + token);
        model.addAttribute("bookings", bookingHistoryClient.getBookings(token, email));

        return "bookings";
    }
}