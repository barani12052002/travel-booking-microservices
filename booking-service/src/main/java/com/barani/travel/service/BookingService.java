package com.barani.travel.service;

import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);

}