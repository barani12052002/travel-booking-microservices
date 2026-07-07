package com.barani.travel.client;

import com.barani.travel.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@FeignClient(
        name = "provider-service",
        contextId = "providerClient",
         url = "${provider.service.url}")
public interface ProviderClient {

    @GetMapping("/provider/timeslots")
    List<TimeslotResponse> getTimeslots();

    @PostMapping("/provider/price")
    PriceResponse getPrice(@RequestBody PriceRequest request);

    @PostMapping("/provider/book")
    BookingResponse createBooking(
            @RequestBody BookingRequest request);

    @GetMapping("/provider/attractions")
    List<AttractionResponse> getAttractions();

    @GetMapping("/provider/attractions/{code}")
    AttractionResponse getAttractionByCode(@PathVariable String code);



}

