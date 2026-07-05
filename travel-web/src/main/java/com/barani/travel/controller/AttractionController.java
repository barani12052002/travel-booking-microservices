package com.barani.travel.controller;

import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.AttractionResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AttractionController {

    private final ProviderClient providerClient;

    public AttractionController(ProviderClient providerClient) {
        this.providerClient = providerClient;
    }

    @GetMapping("/attraction/{code}")
    public String attractionDetails(@PathVariable String code,
                                    Model model) {

        AttractionResponse attraction =
                providerClient.getAttractionByCode(code);

        model.addAttribute("attraction", attraction);

        return "details";
    }

}