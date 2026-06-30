package com.barani.travel.dto.provider;

public class PriceRequest {

    private Integer adultCount;
    private Integer childCount;

    public PriceRequest() {
    }

    public Integer getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(Integer adultCount) {
        this.adultCount = adultCount;
    }

    public Integer getChildCount() {
        return childCount;
    }

    public void setChildCount(Integer childCount) {
        this.childCount = childCount;
    }
}