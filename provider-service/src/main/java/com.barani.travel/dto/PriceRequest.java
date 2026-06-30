package com.barani.travel.dto;

public class PriceRequest {

    private int adultCount;
    private int childCount;

    public PriceRequest() {
    }

    public int getAdultCount() {
        return adultCount;
    }

    public void setAdultCount(int adultCount) {
        this.adultCount = adultCount;
    }

    public int getChildCount() {
        return childCount;
    }

    public void setChildCount(int childCount) {
        this.childCount = childCount;
    }
}