package com.barani.travel.controller;

import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.provider.TimeslotResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController {

    private final ProviderClient providerClient;

    public SearchController(ProviderClient providerClient) {
        this.providerClient = providerClient;
    }

    @GetMapping("/search")
    public String search(Model model){

        model.addAttribute(
                "tours",
                providerClient.getTimeslots());

        return "search";

    }

}