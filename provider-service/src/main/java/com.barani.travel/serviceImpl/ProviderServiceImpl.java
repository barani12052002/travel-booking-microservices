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

        BigDecimal adultPrice = BigDecimal.valueOf(request.getAdultCount() * 1000);

        BigDecimal childPrice = BigDecimal.valueOf(request.getChildCount() * 500);

        BigDecimal subTotal = adultPrice.add(childPrice);

        BigDecimal tax = subTotal.multiply(BigDecimal.valueOf(0.05));

        BigDecimal serviceCharge = BigDecimal.valueOf(25);

        BigDecimal discount = BigDecimal.valueOf(50);

        BigDecimal total = subTotal.add(tax).add(serviceCharge).subtract(discount);

        PriceResponse response = new PriceResponse();

        response.setCurrency("INR");
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

    @Override
    public List<AttractionResponse> getAttractions() {

        return List.of(

                new AttractionResponse(
                        "OOTY001",
                        "Ooty",
                        "Queen of Hill Stations, famous for tea gardens, Nilgiri Mountain Railway, and scenic lakes.",
                        "/images/ooty.png",
                         BigDecimal.valueOf(1999),
                        "2 Days / 1 Night",
                        25,
                        4.8
                ),

                new AttractionResponse(
                        "GOA001",
                        "Goa",
                        "Experience golden beaches, Portuguese heritage, vibrant nightlife, and thrilling water sports.",
                        "/images/goa.png",
                        BigDecimal.valueOf(2999),
                        "3 Days / 2 Nights",
                        30,
                        4.9
                ),

                new AttractionResponse(
                        "KER001",
                        "Kerala",
                        "God's Own Country featuring serene backwaters, lush greenery, houseboats, and peaceful landscapes.",
                        "/images/kerala.png",
                        BigDecimal.valueOf(3499),
                        "4 Days / 3 Nights",
                        18,
                        4.9
                ),

                new AttractionResponse(
                        "KOD001",
                        "Kodaikanal",
                        "The Princess of Hill Stations, known for its peaceful lake, misty hills, waterfalls, and pine forests.",
                        "/images/kodaikanal.png",
                        BigDecimal.valueOf(2499),
                        "2 Days",
                        20,
                        4.7
                ),

                new AttractionResponse(
                        "CHE001",
                        "Chennai",
                        "Explore Marina Beach, historic landmarks, vibrant culture, and authentic South Indian cuisine.",
                        "/images/chennai.png",
                        BigDecimal.valueOf(2299),
                        "2 Days",
                        15,
                        4.6
                )

        );
    }
    @Override
    public AttractionResponse getAttraction(String code) {

        return getAttractions()
                .stream()
                .filter(a -> a.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() ->
                        new RuntimeException("Attraction not found"));
    }

}