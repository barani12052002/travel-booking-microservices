package com.barani.travel.service;

import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;
import com.barani.travel.enums.BookingStatus;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingService {

    BookingResponse createBooking(BookingRequest request);
    BookingResponse getBooking(String bookingReference);
    List<BookingResponse> getBookingsByCustomerEmail(String customerEmail);
    BookingResponse cancelBooking(String bookingReference);
    Page<BookingResponse> getBookings(int page, int size);

    List<BookingResponse> searchBookings(String keyword);

    List<BookingResponse> filterBookings(BookingStatus status);

    List<BookingResponse> searchAndFilterBookings(String keyword,
                                                  BookingStatus status);
}