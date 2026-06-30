package com.barani.travel.dto.provider;

import java.math.BigDecimal;

public class PriceResponse {

    private BigDecimal adultPrice;
    private BigDecimal childPrice;
    private BigDecimal totalPrice;

    public PriceResponse() {
    }

    public PriceResponse(BigDecimal adultPrice,
                         BigDecimal childPrice,
                         BigDecimal totalPrice) {

        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.totalPrice = totalPrice;
    }

    public BigDecimal getAdultPrice() {
        return adultPrice;
    }

    public void setAdultPrice(BigDecimal adultPrice) {
        this.adultPrice = adultPrice;
    }

    public BigDecimal getChildPrice() {
        return childPrice;
    }

    public void setChildPrice(BigDecimal childPrice) {
        this.childPrice = childPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}