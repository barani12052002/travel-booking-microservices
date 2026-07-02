package com.barani.travel.service;

import com.barani.travel.auth.AuthResponse;
import com.barani.travel.auth.LoginRequest;
import com.barani.travel.auth.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}