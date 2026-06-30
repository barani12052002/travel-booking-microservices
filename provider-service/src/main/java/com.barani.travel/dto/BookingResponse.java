package com.barani.travel.dto;

public class BookingResponse {

    private String providerBookingId;

    private String bookingStatus;

    private String message;

    public BookingResponse() {
    }

    public BookingResponse(String providerBookingId,
                           String bookingStatus,
                           String message) {

        this.providerBookingId = providerBookingId;
        this.bookingStatus = bookingStatus;
        this.message = message;
    }

    public String getProviderBookingId() {
        return providerBookingId;
    }

    public void setProviderBookingId(String providerBookingId) {
        this.providerBookingId = providerBookingId;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}