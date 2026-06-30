package com.barani.travel.serviceImpl;

import com.barani.travel.dto.*;
import com.barani.travel.service.ProviderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProviderServiceImpl implements ProviderService {

    @Override
    public List<TimeslotResponse> getTimeslots() {

        List<TimeslotResponse> timeslots = new ArrayList<>();

        timeslots.add(new TimeslotResponse("09:00 AM", true));
        timeslots.add(new TimeslotResponse("10:00 AM", true));
        timeslots.add(new TimeslotResponse("11:00 AM", false));
        timeslots.add(new TimeslotResponse("12:00 PM", true));

        return timeslots;
    }

    @Override
    public PriceResponse getPrice(PriceRequest request) {

        double adultPrice = request.getAdultCount() * 1000;

        double childPrice = request.getChildCount() * 500;

        double totalPrice = adultPrice + childPrice;

        PriceResponse response = new PriceResponse();

        response.setAdultPrice(adultPrice);
        response.setChildPrice(childPrice);
        response.setTotalPrice(totalPrice);

        return response;

    }

    @Override
    public BookingResponse createBooking(BookingRequest request) {

        String bookingId = "ADV-" + UUID.randomUUID()
                .toString()
                .substring(0,8)
                .toUpperCase();

        BookingResponse response = new BookingResponse();

        response.setProviderBookingId(bookingId);
        response.setBookingStatus("SUCCESS");
        response.setMessage("Booking Confirmed Successfully");

        return response;

    }
}