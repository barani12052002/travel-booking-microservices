package com.barani.travel.controller;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Hidden
@RestController
public class DebugController {

    @GetMapping("/me")
    public Authentication me(Authentication authentication) {
        return authentication;
    }
}