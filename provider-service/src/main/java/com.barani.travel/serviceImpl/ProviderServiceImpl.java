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

    @Override
    public List<AttractionResponse> getAttractions() {

        return List.of(

                new AttractionResponse(
                        "OOTY001",
                        "Ooty",
                        "Beautiful hill station with tea estates.",
                        "https://images.unsplash.com/photo-1622308644420-b20142dc993c?auto=format&fit=crop&w=1200&q=80",
                        BigDecimal.valueOf(1999),
                        "2 Days / 1 Night",
                        25,
                        4.8
                ),

                new AttractionResponse(
                        "GOA001",
                        "Goa",
                        "Enjoy beaches, nightlife and adventure.",
                        "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?auto=format&fit=crop&w=1200&q=80",
                        BigDecimal.valueOf(2999),
                        "3 Days / 2 Nights",
                        30,
                        4.9
                ),

                new AttractionResponse(
                        "KER001",
                        "Kerala",
                        "God's Own Country with backwaters.",
                        "https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?auto=format&fit=crop&w=1200&q=80",
                        BigDecimal.valueOf(3499),
                        "4 Days / 3 Nights",
                        18,
                        4.9
                ),

                new AttractionResponse(
                        "KOD001",
                        "Kodaikanal",
                        "Princess of Hill Stations.",
                        "https://images.unsplash.com/photo-1627894483216-2138af692e32?auto=format&fit=crop&w=1200&q=80",
                        BigDecimal.valueOf(2499),
                        "2 Days",
                        20,
                        4.7
                ),

                new AttractionResponse(
                        "CHE001",
                        "Chennai",
                        "Explore palaces and rich heritage.",
                        "https://media.istockphoto.com/id/1211952929/photo/marina-beach-chennai-city-tamil-nadu-india-bay-of-bengal-chennai-tourism-east-coast-road.jpg?s=1024x1024&w=is&k=20&c=XL4-z9Cm8uyt5A9SYQ8BFZdpltTX9HwNTZLiDg_KR-M=",
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