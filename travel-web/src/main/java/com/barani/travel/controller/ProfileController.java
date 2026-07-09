package com.barani.travel.controller;

import com.barani.travel.auth.ChangePasswordRequest;
import com.barani.travel.auth.UpdateProfileRequest;
import com.barani.travel.client.AuthClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProfileController {

    private final AuthClient authClient;

    public ProfileController(AuthClient authClient) {
        this.authClient = authClient;
    }

    @GetMapping("/profile")
    public String profile(HttpSession session, Model model) {

        String token = "Bearer " + session.getAttribute("TOKEN");

        model.addAttribute("profile", authClient.getProfile(token));

        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@ModelAttribute UpdateProfileRequest request, HttpSession session) {

        String token = "Bearer " + session.getAttribute("TOKEN");

        authClient.updateProfile(token, request);

        return "redirect:/profile";
    }

    @GetMapping("/change-password")
    public String changePasswordPage() {

        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@ModelAttribute ChangePasswordRequest request, HttpSession session) {

        String token = "Bearer " + session.getAttribute("TOKEN");

        authClient.changePassword(token, request);

        return "redirect:/profile";
    }

}