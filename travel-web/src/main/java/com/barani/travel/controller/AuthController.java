package com.barani.travel.controller;

import com.barani.travel.auth.AuthResponse;
import com.barani.travel.auth.LoginRequest;
import com.barani.travel.auth.RegisterRequest;
import com.barani.travel.client.AuthClient;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthClient authClient;

    public AuthController(AuthClient authClient) {
        this.authClient = authClient;
    }

    @PostMapping("/register")
    public String register(@ModelAttribute RegisterRequest request) {

        authClient.register(request);

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest request,
                        HttpSession session) {

        AuthResponse response = authClient.login(request);

        log.info("JWT generated TOKEN {}", response.getAccessToken());
        log.info("USERNAME {}", response.getUsername());
        log.info("ROLE {}", response.getRole());


        session.setAttribute("TOKEN", response.getAccessToken());
        session.setAttribute("REFRESH_TOKEN", response.getRefreshToken());
        session.setAttribute("USERNAME", response.getUsername());
        session.setAttribute("EMAIL", response.getEmail());
        session.setAttribute("ROLE", response.getRole());
        log.info("REFRESH TOKEN {}",  response.getRefreshToken());


        return "redirect:/dashboard";
    }

}