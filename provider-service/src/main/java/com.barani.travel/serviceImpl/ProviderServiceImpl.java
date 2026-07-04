package com.barani.travel.serviceImpl;

import com.barani.travel.dto.*;
import com.barani.travel.service.ProviderService;
import enums.Attraction;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
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

        BigDecimal adultPrice = BigDecimal.valueOf(request.getAdultCount() * 1000);

        BigDecimal childPrice = BigDecimal.valueOf(request.getChildCount() * 500);

        BigDecimal subTotal = adultPrice.add(childPrice);

        BigDecimal tax = subTotal.multiply(BigDecimal.valueOf(0.05));

        BigDecimal serviceCharge = BigDecimal.valueOf(25);

        BigDecimal discount = BigDecimal.valueOf(50);

        BigDecimal total = subTotal.add(tax).add(serviceCharge).subtract(discount);

        PriceResponse response = new PriceResponse();

        response.setCurrency("AED");
        response.setAdultPrice(adultPrice);
        response.setChildPrice(childPrice);
        response.setTax(tax);
        response.setServiceCharge(serviceCharge);
        response.setDiscount(discount);
        response.setTotalPrice(total);

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
    public List<AttractionResponse> getAttractions() {

        return Arrays.stream(Attraction.values())
                .map(a -> new AttractionResponse(
                        a.getCode(),
                        a.getDisplayName()
                ))
                .toList();

    }
}