package com.barani.travel.controller;

import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.*;
import com.barani.travel.dto.provider.PriceRequest;
import com.barani.travel.dto.provider.PriceResponse;
import com.barani.travel.dto.provider.TimeslotResponse;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Hidden
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