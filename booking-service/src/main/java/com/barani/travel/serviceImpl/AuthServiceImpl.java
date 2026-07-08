package com.barani.travel.serviceImpl;

import com.barani.travel.auth.*;
import com.barani.travel.entity.RefreshToken;
import com.barani.travel.entity.User;
import com.barani.travel.enums.Role;
import com.barani.travel.repository.UserRepository;
import com.barani.travel.security.JwtService;
import com.barani.travel.service.AuthService;
import com.barani.travel.service.RefreshTokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           RefreshTokenService refreshTokenService) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered.");
        }

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        // Encrypt password
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Default role
        user.setRole(Role.USER);

        userRepository.save(user);

        String token = jwtService.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        AuthResponse response = new AuthResponse();

        response.setAccessToken(token);
        response.setRefreshToken(refreshToken.getToken());
        response.setUsername(user.getUsername());

        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        return response;
    }

    @Override
    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user.getEmail());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);
        AuthResponse response = new AuthResponse();

        response.setAccessToken(token);
        response.setRefreshToken(refreshToken.getToken());
        response.setUsername(user.getUsername());
        response.setRole(user.getRole().name());
        response.setEmail(user.getEmail());


        return response;
    }

    @Override
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken oldToken =
                refreshTokenService.verifyRefreshToken(request.getRefreshToken());

        User user = oldToken.getUser();

        // Remove old refresh token
        refreshTokenService.deleteByUser(user);

        // Create a new refresh token
        RefreshToken newToken =
                refreshTokenService.createRefreshToken(user);

        // Generate a new access token
        String accessToken =
                jwtService.generateToken(user.getEmail());

        return new RefreshTokenResponse(
                accessToken,
                newToken.getToken()
        );
    }

    @Override
    public void logout(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        refreshTokenService.deleteByUser(user);
    }
}