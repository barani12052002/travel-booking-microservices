package com.barani.travel.client;

import com.barani.travel.dto.BookingResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "booking-service",
        contextId = "bookingHistoryClient",
        url = "${booking.service.url}"
)
public interface BookingHistoryClient {

    @GetMapping("/booking/customer/{email}")
    List<BookingResponse> getBookings(
            @PathVariable String email);

}