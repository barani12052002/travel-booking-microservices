package com.barani.travel.provider;

import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;
import com.barani.travel.dto.provider.PriceRequest;
import com.barani.travel.dto.provider.PriceResponse;


public interface BookingProvider {

    String getProviderCode();

    BookingResponse createBooking(BookingRequest request);

    PriceResponse getPrice(PriceRequest request);

}