package com.barani.travel.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
//travel-web
    private String accessToken;

    private String refreshToken;

    private String username;

    private String email;

    private String phone;

    public AuthResponse() {
    }

}