package com.barani.travel.controller;

import com.barani.travel.auth.AdminDashboardResponse;
import com.barani.travel.auth.UserResponse;
import com.barani.travel.dto.BookingResponse;
import com.barani.travel.enums.BookingStatus;
import com.barani.travel.service.AdminService;
import com.barani.travel.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
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
    public Page<BookingResponse> bookings(@RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        return bookingService.getBookings(page, size);
    }

    @GetMapping("/test")
    public String test(Authentication authentication) {

       log.info("Authentication {}",authentication.getAuthorities());

        return authentication.getAuthorities().toString();
    }

    @GetMapping("/users/search")
    public List<UserResponse> searchUsers(@RequestParam String keyword) {

        return adminService.searchUsers(keyword);
    }

    @GetMapping("/users")
    public List<UserResponse> users(@RequestParam(required = false) String keyword) {

        if (keyword == null || keyword.isBlank()) {
            return adminService.getUsers();
        }

        return adminService.searchUsers(keyword);
    }
}