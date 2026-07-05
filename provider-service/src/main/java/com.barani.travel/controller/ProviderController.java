package com.barani.travel.controller;

import com.barani.travel.dto.*;
import com.barani.travel.service.ProviderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provider")
public class ProviderController {
    private final ProviderService providerService;

    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping("/timeslots")
    public List<TimeslotResponse> getTimeslots() {
        return providerService.getTimeslots();
    }

    @PostMapping("/price")
    public PriceResponse getPrice(@RequestBody PriceRequest request){
        return providerService.getPrice(request);

    }

    @PostMapping("/book")
    public BookingResponse createBooking(@RequestBody BookingRequest request){

        return providerService.createBooking(request);

    }

    @GetMapping("/attractions")
    public List<AttractionResponse> getAttractions() {

        return providerService.getAttractions();

    }

    @GetMapping("/attractions/{code}")
    public AttractionResponse getAttraction(@PathVariable String code) {

        return providerService.getAttraction(code);

    }
}