package com.barani.travel.service;

import com.barani.travel.dto.*;

import java.util.List;

public interface ProviderService {

    List<TimeslotResponse> getTimeslots();

    PriceResponse getPrice(PriceRequest request);

    BookingResponse createBooking(BookingRequest request);

    List<AttractionResponse> getAttractions();

    AttractionResponse getAttraction(String code);

}