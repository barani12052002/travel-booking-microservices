package com.barani.travel.controller;

import com.barani.travel.auth.AdminDashboardResponse;
import com.barani.travel.dto.BookingResponse;
import com.barani.travel.service.AdminService;
import com.barani.travel.service.BookingService;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;
    private final BookingService bookingService;
    public AdminController(AdminService adminService, BookingService bookingService) {

        this.adminService = adminService;
        this.bookingService = bookingService;
    }

    @GetMapping("/dashboard")
    public AdminDashboardResponse dashboard() {

        return adminService.dashboard();
    }

    @GetMapping("/bookings")
    public Page<BookingResponse> bookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return bookingService.getBookings(page, size);
    }

    @GetMapping("/test")
    public String test(Authentication authentication) {

        System.out.println(authentication.getAuthorities());

        return authentication.getAuthorities().toString();
    }

}