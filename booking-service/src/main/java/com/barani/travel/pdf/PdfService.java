package com.barani.travel.pdf;

import com.barani.travel.entity.Booking;

public interface PdfService {

    byte[] generateBookingPdf(Booking booking);

}