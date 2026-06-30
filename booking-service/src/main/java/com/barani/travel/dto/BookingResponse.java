package com.barani.travel.dto;

import java.math.BigDecimal;

public class BookingResponse {

    private String bookingReference;

    private String bookingStatus;

    private BigDecimal totalAmount;
    private String providerBookingId;
    private String message;

    public String getProviderBookingId() {
        return providerBookingId;
    }

    public void setProviderBookingId(String providerBookingId) {
        this.providerBookingId = providerBookingId;
    }

    public String getBookingReference() {
        return bookingReference;
    }

    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}