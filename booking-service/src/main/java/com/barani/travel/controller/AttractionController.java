package com.barani.travel.controller;

import com.barani.travel.dto.AttractionResponse;
import com.barani.travel.enums.Attraction;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/attractions")
public class AttractionController {

    @GetMapping
    public List<AttractionResponse> getAll(){

        return Arrays.stream(Attraction.values())
                .map(a -> new AttractionResponse(
                        a.getCode(),
                        a.getDisplayName()))
                .toList();

    }

}