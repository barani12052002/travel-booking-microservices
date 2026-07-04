package com.barani.travel.controller;

import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookingController {

    private final ProviderClient providerClient;

    public BookingController(ProviderClient providerClient) {
        this.providerClient = providerClient;
    }

    @GetMapping("/booking")
    public String bookingPage(Model model) {

        model.addAttribute("booking", new BookingRequest());

        return "booking";
    }

    @PostMapping("/booking")
    public String createBooking(@ModelAttribute BookingRequest booking,
                                Model model) {

        BookingResponse response =
                providerClient.createBooking(booking);

        model.addAttribute("response", response);

        return "success";
    }

}