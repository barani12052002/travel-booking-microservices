package com.barani.travel.service;

public interface EmailService {

    void sendBookingConfirmation(String to, String subject, String body, byte[] pdf,
                                 String fileName);

}