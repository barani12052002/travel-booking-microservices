package com.barani.travel.controller;

import com.barani.travel.client.BookingClient;
import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;
import com.barani.travel.dto.PriceRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BookingController {

    private final ProviderClient providerClient;
    private final BookingClient bookingClient;

    public BookingController(ProviderClient providerClient,
                             BookingClient bookingClient) {
        this.providerClient = providerClient;
        this.bookingClient = bookingClient;
    }


    @GetMapping("/booking")
    public String booking(@RequestParam String code,
                          @RequestParam String travelDate,
                          @RequestParam Integer adults,
                          @RequestParam Integer children,
                          Model model) {

        model.addAttribute("tour",
                providerClient.getAttraction(code));

        model.addAttribute("travelDate", travelDate);
        model.addAttribute("adults", adults);
        model.addAttribute("children", children);

        model.addAttribute("timeslots",
                providerClient.getTimeslots());

        return "booking";
    }


        @PostMapping("/confirm-booking")
        public String confirmBooking(@RequestParam String code,
                                     Model model) {

            BookingRequest request = new BookingRequest();

            request.setProviderCode(code);

            BookingResponse response =
                    bookingClient.createBooking(request);

            model.addAttribute("booking", response);

            return "success";
        }

    }
