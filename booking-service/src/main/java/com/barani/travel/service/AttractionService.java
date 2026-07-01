package com.barani.travel.service;

import com.barani.travel.dto.AttractionResponse;

import java.util.List;

public interface AttractionService {

    List<AttractionResponse> getAllAttractions();

    AttractionResponse getByCode(String attractionCode);

    List<AttractionResponse> getByCity(String city);

}