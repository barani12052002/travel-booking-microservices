package com.barani.travel.controller;

import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.AttractionResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class SearchController {

    private final ProviderClient providerClient;

    public SearchController(ProviderClient providerClient) {
        this.providerClient = providerClient;
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false) String destination,
                         Model model) {

        List<AttractionResponse> attractions = providerClient.getAttractions();

        if (destination != null && !destination.isBlank()) {

            String search = destination.toLowerCase();

            attractions = attractions.stream()
                    .filter(a ->
                            (a.getDisplayName() != null && a.getDisplayName().toLowerCase().contains(search))
                                    || (a.getName() != null && a.getName().toLowerCase().contains(search))
                                    || (a.getCity() != null && a.getCity().toLowerCase().contains(search))
                                    || (a.getCountry() != null && a.getCountry().toLowerCase().contains(search))
                    )
                    .toList();
        }

        model.addAttribute("attractions", attractions);

        return "search";
    }
    @GetMapping("/details")
    public String details(@RequestParam String code,
                          Model model) {

        model.addAttribute(
                "tour",
                providerClient.getAttractionByCode(code)
        );

        return "details";
    }

}