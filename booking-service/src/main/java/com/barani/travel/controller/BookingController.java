package com.barani.travel.controller;

import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;
import com.barani.travel.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(summary = "Create Booking")
    @PostMapping
    public BookingResponse createBooking(@Valid @RequestBody BookingRequest request){

        return bookingService.createBooking(request);

    }

    @Operation(summary = "Get Booking By Reference")
    @GetMapping("/{bookingReference}")
    public BookingResponse getBooking(@PathVariable String bookingReference) {

        return bookingService.getBooking(bookingReference);
    }

    @GetMapping("/customer/{email}")
    public List<BookingResponse> getBookings(@PathVariable String email){

        return bookingService.getBookingsByEmail(email);

    }
}
