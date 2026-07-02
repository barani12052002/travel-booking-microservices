package com.barani.travel.serviceImpl;

import com.barani.travel.entity.RefreshToken;
import com.barani.travel.entity.User;
import com.barani.travel.repository.RefreshTokenRepository;
import com.barani.travel.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshTokenServiceImpl(RefreshTokenRepository repository) {
        this.repository = repository;
    }

    @Override
    public RefreshToken createRefreshToken(User user) {

        repository.deleteByUserId(user.getId());

        RefreshToken token = new RefreshToken();

        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());

        token.setExpiryDate(
                LocalDateTime.now().plusDays(7));

        return repository.save(token);
    }

    @Override
    public RefreshToken verifyRefreshToken(String token) {

        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() ->
                        new RuntimeException("Invalid Refresh Token"));

        if (refreshToken.getExpiryDate().isBefore(LocalDateTime.now())) {

            repository.delete(refreshToken);

            throw new RuntimeException("Refresh Token Expired");
        }

        return refreshToken;
    }

    @Override
    public void deleteByUser(User user) {

        repository.deleteByUserId(user.getId());

    }
}