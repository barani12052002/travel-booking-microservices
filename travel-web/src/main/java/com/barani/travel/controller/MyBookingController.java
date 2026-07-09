package com.barani.travel.controller;

import com.barani.travel.client.BookingHistoryClient;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MyBookingController {

    private static final Logger log = LoggerFactory.getLogger(MyBookingController.class);
    private final BookingHistoryClient bookingHistoryClient;

    public MyBookingController(BookingHistoryClient bookingHistoryClient) {
        this.bookingHistoryClient = bookingHistoryClient;
    }

    @GetMapping("/bookings")
    public String bookings(HttpSession session, Model model) {

        String email = (String) session.getAttribute("EMAIL");

        String token = "Bearer " + session.getAttribute("TOKEN");

        log.info("EMAIL = {}",  email);
        log.info("TOKEN = {}",  token);

        model.addAttribute("bookings", bookingHistoryClient.getBookings(token, email));

        return "bookings";
    }

    @PostMapping("/bookings/cancel/{bookingReference}")
    public String cancelBooking(@PathVariable String bookingReference, HttpSession session) {

        String token = "Bearer " + session.getAttribute("TOKEN");

        bookingHistoryClient.cancelBooking(token, bookingReference);

        return "redirect:/bookings";
    }
}