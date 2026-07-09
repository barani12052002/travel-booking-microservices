package com.barani.travel.client;

import com.barani.travel.auth.AdminDashboardResponse;
import com.barani.travel.auth.UserResponse;
import com.barani.travel.dto.BookingResponse;
import org.springframework.data.domain.Page;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "booking-service", contextId = "adminClient")
public interface AdminClient {

    @GetMapping("/admin/users")
    List<UserResponse> getUsers(@RequestHeader("Authorization") String token,
            @RequestParam(required = false) String keyword);

    @GetMapping("/admin/dashboard")
    AdminDashboardResponse dashboard(@RequestHeader("Authorization") String token);


    @GetMapping("/admin/bookings")
    Page<BookingResponse> getBookings(@RequestHeader("Authorization") String token,
            @RequestParam("page") int page,
            @RequestParam("size") int size);

    @PutMapping("/booking/cancel/{bookingReference}")
    BookingResponse cancelBooking(@RequestHeader("Authorization") String token,
            @PathVariable String bookingReference);
}

