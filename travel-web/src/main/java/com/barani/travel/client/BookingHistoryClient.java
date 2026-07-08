package com.barani.travel.client;

import com.barani.travel.dto.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
@FeignClient(name = "booking-service", contextId = "bookingHistoryClient")
public interface BookingHistoryClient {

    @GetMapping("/booking/customer/{email}")
    List<BookingResponse> getBookings(@RequestHeader("Authorization") String token, @PathVariable String email);

    @PutMapping("/booking/cancel/{bookingReference}")
    BookingResponse cancelBooking(@RequestHeader("Authorization") String token,
            @PathVariable String bookingReference);
}