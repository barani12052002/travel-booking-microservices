package com.barani.travel.controller;

import com.barani.travel.security.JwtService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final JwtService jwtService;

    public TestController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @GetMapping("/token")
    public String token() {

        return jwtService.generateToken("baranibarani732@gmail.com");
    }
}