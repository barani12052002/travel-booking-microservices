package com.barani.travel.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenResponse {

    private String accessToken;
    private String refreshToken;

    public RefreshTokenResponse() {
    }

    public RefreshTokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}