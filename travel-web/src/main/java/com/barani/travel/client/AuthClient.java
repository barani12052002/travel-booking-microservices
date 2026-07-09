package com.barani.travel.client;

import com.barani.travel.auth.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "booking-service", contextId = "authClient")
public interface AuthClient {

    @PostMapping("/auth/register")
    AuthResponse register(@RequestBody RegisterRequest request);

    @PostMapping("/auth/login")
    AuthResponse login(@RequestBody LoginRequest request);

    @PostMapping("/auth/logout")
    void logout(@RequestHeader("Authorization") String token);

    @GetMapping("/auth/profile")
    ProfileResponse getProfile(@RequestHeader("Authorization") String token);

    @PutMapping("/auth/profile")
    ProfileResponse updateProfile(@RequestHeader("Authorization") String token,
            @RequestBody UpdateProfileRequest request);

    @PutMapping("/auth/change-password")
    void changePassword(@RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordRequest request);
}