package com.barani.travel.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenResponse {

    private String accessToken;

    private String refreshToken;

}