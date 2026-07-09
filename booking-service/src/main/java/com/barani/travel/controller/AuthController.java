package com.barani.travel.controller;

import com.barani.travel.auth.*;
import com.barani.travel.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {

        return authService.login(request);
    }

    @PostMapping("/refresh")
    public RefreshTokenResponse refreshToken(@RequestBody RefreshTokenRequest request) {

        return authService.refreshToken(request);
    }

    @PostMapping("/logout")
    public void logout(Authentication authentication) {
        authService.logout(authentication.getName());
    }

    @GetMapping("/profile")
    public ProfileResponse profile(Authentication authentication) {

        return authService.getProfile(authentication.getName());
    }

    @PutMapping("/profile")
    public ProfileResponse updateProfile(Authentication authentication,@Valid
            @RequestBody UpdateProfileRequest request) {

        return authService.updateProfile(authentication.getName(), request);
    }

    @PutMapping("/change-password")
    public void changePassword(Authentication authentication,@Valid
            @RequestBody ChangePasswordRequest request) {

        authService.changePassword(authentication.getName(), request);
    }
}