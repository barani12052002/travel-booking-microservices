package com.barani.travel.controller;

import com.barani.travel.auth.AuthResponse;
import com.barani.travel.auth.LoginRequest;
import com.barani.travel.auth.RegisterRequest;
import com.barani.travel.client.AuthClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

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

        AuthResponse response =
                authClient.login(request);
        System.out.println("TOKEN = " + response.getAccessToken());
        System.out.println("USERNAME = " + response.getUsername());
        System.out.println("EMAIL = " + response.getEmail());
        session.setAttribute("TOKEN",
                response.getAccessToken());

        session.setAttribute("USERNAME",
                response.getUsername());

        session.setAttribute("EMAIL",
                response.getEmail());


        return "redirect:/dashboard";
    }

}