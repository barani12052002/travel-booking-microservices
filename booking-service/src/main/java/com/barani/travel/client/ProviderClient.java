package com.barani.travel.client;

import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;
import com.barani.travel.dto.PriceRequest;
import com.barani.travel.dto.PriceResponse;
import com.barani.travel.dto.TimeslotResponse;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "provider-service")
public interface ProviderClient {

    @GetMapping("/provider/timeslots")
    List<TimeslotResponse> getTimeslots();

    @PostMapping("/provider/price")
    PriceResponse getPrice(@RequestBody PriceRequest request);

    @PostMapping("/provider/book")
    BookingResponse createBooking(
            @RequestBody BookingRequest request);
}