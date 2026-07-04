package com.barani.travel.controller;

import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.AttractionResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SearchController {

    private final ProviderClient providerClient;

    public SearchController(ProviderClient providerClient) {
        this.providerClient = providerClient;
    }

    @GetMapping("/search")
    public String search(Model model){

        model.addAttribute(
                "attractions",
                providerClient.getAttractions());

        return "search";
    }

}