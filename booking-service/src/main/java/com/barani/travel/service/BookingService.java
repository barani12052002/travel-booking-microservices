package com.barani.travel.service;

import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);
    BookingResponse getBooking(String bookingReference);
    List<BookingResponse> getBookingsByEmail(String email);

}