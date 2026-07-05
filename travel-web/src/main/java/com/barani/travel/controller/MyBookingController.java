package com.barani.travel.controller;

import com.barani.travel.client.BookingHistoryClient;
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
    public String bookings(Model model) {

        String email = "baraniece007@gmail.com";

        model.addAttribute(
                "bookings",
                bookingHistoryClient.getBookings(email));

        return "bookings";
    }
}