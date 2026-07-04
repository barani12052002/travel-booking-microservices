package com.barani.travel.dto;

public class AttractionResponse {

    private String attractionCode;

    private String attractionName;

    private String city;

    private String country;

    private String providerName;

    private String currency;

    public AttractionResponse(String code, String displayName) {
    }

    public String getAttractionCode() {
        return attractionCode;
    }

    public void setAttractionCode(String attractionCode) {
        this.attractionCode = attractionCode;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}