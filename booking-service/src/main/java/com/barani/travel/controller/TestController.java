package com.barani.travel.controller;

import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    private final ProviderClient providerClient;

    public TestController(ProviderClient providerClient) {
        this.providerClient = providerClient;
    }

    @GetMapping("/timeslots")
    public List<TimeslotResponse> getTimeslots() {
        return providerClient.getTimeslots();
    }

    @PostMapping("/price")
    public PriceResponse getPrice(@RequestBody PriceRequest request) {
        return providerClient.getPrice(request);
    }
    @PostMapping("/book")
    public BookingResponse createBooking(@RequestBody BookingRequest request) {

        return providerClient.createBooking(request);

    }
}