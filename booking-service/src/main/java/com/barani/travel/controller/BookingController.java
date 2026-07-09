package com.barani.travel.controller;

import com.barani.travel.dto.BookingRequest;
import com.barani.travel.dto.BookingResponse;
import com.barani.travel.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {
    private static final Logger log = LoggerFactory.getLogger(BookingController.class);
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Create Booking")
    @PostMapping
    public BookingResponse createBooking(@Valid @RequestBody BookingRequest request){

        return bookingService.createBooking(request);

    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @Operation(summary = "Get Booking By Reference")
    @GetMapping("/{bookingReference}")
    public BookingResponse getBooking(@PathVariable String bookingReference) {

        return bookingService.getBooking(bookingReference);
    }

    @GetMapping("/customer/{email}")
    public List<BookingResponse> getBookingsByCustomerEmail(@PathVariable String email) {
        log.info("Booking history endpoint reached for user {}", email);
        return bookingService.getBookingsByCustomerEmail(email);
    }
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PutMapping("/cancel/{bookingReference}")
    public BookingResponse cancelBooking(@PathVariable String bookingReference){

        return bookingService.cancelBooking(bookingReference);

    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public Page<BookingResponse> getBookings(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return bookingService.getBookings(page, size);
    }
}
