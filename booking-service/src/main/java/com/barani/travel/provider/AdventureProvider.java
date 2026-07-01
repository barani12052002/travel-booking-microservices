package com.barani.travel.provider;

import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.*;
import com.barani.travel.dto.provider.PriceRequest;
import com.barani.travel.dto.provider.PriceResponse;
import com.barani.travel.dto.provider.TimeslotResponse;
import org.springframework.stereotype.Service;

@Service
public class AdventureProvider implements BookingProvider {

    private final ProviderClient providerClient;

    public AdventureProvider(ProviderClient providerClient) {
        this.providerClient = providerClient;
    }

    @Override
    public String getProviderCode() {
        return "ADV";
    }

    @Override
    public BookingResponse createBooking(BookingRequest request) {
        return providerClient.createBooking(request);
    }

    @Override
    public PriceResponse getPrice(PriceRequest request) {
        return providerClient.getPrice(request);
    }
}