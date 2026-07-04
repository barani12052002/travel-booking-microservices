package com.barani.travel.service;

import com.barani.travel.dto.*;

import java.util.Arrays;
import java.util.List;

public interface ProviderService {

    List<TimeslotResponse> getTimeslots();

    PriceResponse getPrice(PriceRequest request);

    BookingResponse createBooking(BookingRequest request);

    public default List<AttractionResponse> getAttractions() {

        return Arrays.stream(Attraction.values())
                .map(attraction -> new AttractionResponse(
                        attraction.getCode(),
                        attraction.getDisplayName()
                ))
                .toList();
    }
}