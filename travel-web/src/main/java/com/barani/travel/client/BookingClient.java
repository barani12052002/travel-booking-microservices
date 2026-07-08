package com.barani.travel.client;

import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "booking-service",
        contextId = "bookingClient"
)
public interface BookingClient {

    @PostMapping("/booking")
    BookingResponse createBooking(
            @RequestHeader("Authorization") String token,
            @RequestBody BookingRequest request);

}