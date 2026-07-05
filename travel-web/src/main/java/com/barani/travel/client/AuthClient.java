package com.barani.travel.client;

import com.barani.travel.auth.AuthResponse;
import com.barani.travel.auth.LoginRequest;
import com.barani.travel.auth.RegisterRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "booking-service",
        contextId = "authClient",
        url = "${booking.service.url}"
)
public interface AuthClient {

    @PostMapping("/auth/register")
    AuthResponse register(@RequestBody RegisterRequest request);

    @PostMapping("/auth/login")
    AuthResponse login(@RequestBody LoginRequest request);

}