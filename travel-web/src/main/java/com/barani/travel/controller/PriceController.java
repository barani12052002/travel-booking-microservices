package com.barani.travel.controller;

import com.barani.travel.client.ProviderClient;
import com.barani.travel.dto.AttractionResponse;
import com.barani.travel.dto.PriceRequest;
import com.barani.travel.dto.PriceResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PriceController {

    private final ProviderClient providerClient;

    public PriceController(ProviderClient providerClient) {
        this.providerClient = providerClient;
    }

    @PostMapping("/price")
    public String price(@RequestParam String code,
                        @RequestParam String travelDate,
                        @RequestParam Integer adults,
                        @RequestParam Integer children,
                        @RequestParam String time,
                        Model model) {

        AttractionResponse attraction =
                providerClient.getAttraction(code);

        PriceRequest request = new PriceRequest();

        request.setAdultCount(adults);
        request.setChildCount(children);

        PriceResponse price =
                providerClient.getPrice(request);

        model.addAttribute("tour", attraction);
        model.addAttribute("price", price);

        model.addAttribute("travelDate", travelDate);
        model.addAttribute("adults", adults);
        model.addAttribute("children", children);
        model.addAttribute("time", time);

        return "price";
    }

}