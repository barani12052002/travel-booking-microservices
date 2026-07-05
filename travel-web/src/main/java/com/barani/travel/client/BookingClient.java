package com.barani.travel.client;

import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name="booking-service",
        url="${booking.service.url}"
)
public interface BookingClient {

    @PostMapping("/booking")
    BookingResponse createBooking(
            @RequestBody BookingRequest request);

}