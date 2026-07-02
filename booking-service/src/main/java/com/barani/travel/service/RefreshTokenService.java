package com.barani.travel.service;

import com.barani.travel.entity.RefreshToken;
import com.barani.travel.entity.User;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(User user);

    RefreshToken verifyRefreshToken(String token);

    void deleteByUser(User user);

}