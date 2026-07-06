package com.barani.travel.controller;

import com.barani.travel.client.BookingClient;
import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

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
                                 @RequestParam String travelDate,
                                 @RequestParam Integer adults,
                                 @RequestParam Integer children,
                                 @RequestParam String time,
                                 @RequestParam BigDecimal totalAmount,
                                 @RequestParam String currency,
                                 HttpSession session,
                                 Model model) {

        BookingRequest request = new BookingRequest();

        request.setCustomerName(
                (String) session.getAttribute("USERNAME"));

        request.setCustomerEmail(
                (String) session.getAttribute("EMAIL"));

        request.setCustomerPhone(
                (String) session.getAttribute("PHONE"));

        request.setAttractionCode(code);

        request.setTravelDate(LocalDate.parse(travelDate));

        request.setTimeSlot(time);

        request.setAdultCount(adults);

        request.setChildCount(children);
        request.setTotalAmount(totalAmount);

        request.setCurrency(currency);
        String token = "Bearer " +
                (String) session.getAttribute("TOKEN");

        BookingResponse response =
                bookingClient.createBooking(token, request);

        model.addAttribute("booking", response);

        return "success";
    }

    }
