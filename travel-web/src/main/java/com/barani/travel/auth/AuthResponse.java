package com.barani.travel.auth;

import lombok.Getter;

@Getter
public class AuthResponse {

    private final String accessToken;
    private final String refreshToken;

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
        this.refreshToken = null;
    }
}