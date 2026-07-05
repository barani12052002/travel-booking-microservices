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

        request.setProviderCode(code);
        request.setAttractionCode(code);

        request.setTravelDate(LocalDate.parse(travelDate));

        request.setAdultCount(adults);
        request.setChildCount(children);

        request.setTimeSlot(time);

        request.setTotalAmount(totalAmount);

        request.setCurrency(currency);

        // Temporary values until we use JWT
        request.setCustomerName("Barani");

        request.setCustomerEmail("baraniece007@gmail.com");

        request.setCustomerPhone("9876543210");

        BookingResponse response =
                bookingClient.createBooking(request);

        model.addAttribute("booking", response);

        return "success";
    }

    }
