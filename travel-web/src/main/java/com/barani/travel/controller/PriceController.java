package com.barani.travel.controller;

import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.PriceRequest;
import com.barani.travel.dto.PriceResponse;
import org.springframework.web.bind.annotation.*;

@RestController
public class PriceController {

    private final ProviderClient providerClient;

    public PriceController(ProviderClient providerClient) {
        this.providerClient = providerClient;
    }

    @PostMapping("/price")
    public PriceResponse calculatePrice(@RequestBody PriceRequest request) {

        return providerClient.getPrice(request);

    }

}